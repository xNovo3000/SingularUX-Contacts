package org.singularux.contacts.data.contacts.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.core.permission.ContactsPermission;
import org.singularux.contacts.core.permission.ContactsPermissionManager;
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class ContactsRepositoryAndroid implements ContactsRepository {

    private static final String TAG = "ContactRepositoryAndroid";

    private static final int FILTER_QUERY_MAX_SIZE = 20;

    private final Context context;
    private final ContactsPermissionManager contactsPermissionManager;

    @Override
    public @NonNull List<ContactBriefEntity> getAll() {
        Log.d(TAG, "Requested all contacts");
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.i(TAG, "Permission READ_CONTACTS not granted");
            return Collections.emptyList();
        }
        // Generate query arguments
        val uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = {
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                ContactsContract.Contacts.STARRED
        };
        val queryArgs = new Bundle();
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?");
        queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{"1"});
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, ContactsContract.Contacts.SORT_KEY_PRIMARY);
        // Create utility classes
        val extractor = new Extractors.IContactBriefEntity();
        // Make query and extract data
        try (val cursor = context.getContentResolver().query(uri, projection, queryArgs, null)) {
            val result = new ArrayList<ContactBriefEntity>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    result.add(extractor.apply(cursor));
                }
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Cannot execute getAll query", e);
            return Collections.emptyList();
        }
    }

    @Override
    public @NonNull List<ContactBriefEntity> getByDisplayNameLike(@NonNull String query) {
        Log.d(TAG, "Requested contacts with query: " + query);
        // Check query length
        if (query.trim().isEmpty()) {
            Log.i(TAG, "Query is empty");
            return Collections.emptyList();
        }
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.i(TAG, "Permission READ_CONTACTS not granted");
            return Collections.emptyList();
        }
        // Generate query arguments
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, query);
        String[] projection = {
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                ContactsContract.Contacts.STARRED
        };
        val queryArgs = new Bundle();
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?");
        queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{"1"});
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, ContactsContract.Contacts.SORT_KEY_PRIMARY);
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_LIMIT, String.valueOf(FILTER_QUERY_MAX_SIZE));
        // Create utility classes
        val extractor = new Extractors.IContactBriefEntity();
        // Make query and extract data
        try (val cursor = context.getContentResolver().query(uri, projection, queryArgs, null)) {
            val result = new ArrayList<ContactBriefEntity>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    result.add(extractor.apply(cursor));
                }
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Cannot execute getByDisplayNameLike query", e);
            return Collections.emptyList();
        }
    }

    @Override
    public @Nullable ContactBriefEntity getByLookupKey(String lookupKey) {
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.i(TAG, "Permission READ_CONTACTS not granted");
            return null;
        }
        // Extract main contact
        var uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        String[] projection = {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI,
                ContactsContract.Contacts.STARRED
        };
        val queryArgs = new Bundle();
        // Create utility classes
        val extractor = new Extractors.IContactBriefEntity();
        // Make query and extract data
        ContactBriefEntity contactBriefEntity = null;
        try (val cursor = context.getContentResolver().query(uri, projection, queryArgs, null)) {
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    contactBriefEntity = extractor.apply(cursor);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Cannot execute getByLookupKey::contactBriefEntity query", e);
            return null;
        }
        // Extract phone numbers and email addresses
        uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        projection = new String[]{
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.DATA1,
                ContactsContract.Data.DATA2,
                ContactsContract.Data.DATA3
        };

        return null;
    }

}
