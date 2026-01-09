package org.singularux.contacts.data.contacts.repository;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.core.permission.ContactsPermission;
import org.singularux.contacts.core.permission.ContactsPermissionManager;
import org.singularux.contacts.data.contacts.DataContactsUri;
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.data.contacts.entity.ContactEntity;
import org.singularux.contacts.data.contacts.entity.EmailAddressEntity;
import org.singularux.contacts.data.contacts.entity.PhoneNumberEntity;
import org.singularux.contacts.data.contacts.entity.PhotoEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class ContactsRepositoryAndroid implements ContactsRepository {

    private static final String TAG = "ContactRepositoryAndroid";

    private static final String[] PROJECTION_CONTACT = {
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.Contacts.STARRED
    };
    private static final String[] PROJECTION_DATA = {
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.DATA3,
            ContactsContract.Data.DATA14
    };
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
        val queryArgs = new Bundle();
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?");
        queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{"1"});
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, ContactsContract.Contacts.SORT_KEY_PRIMARY);
        // Create utility classes
        val extractor = new Extractors.IContactBriefEntity();
        // Make query and extract data
        try (val cursor = context.getContentResolver().query(uri, PROJECTION_CONTACT, queryArgs, null)) {
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
        if (query.trim().isBlank()) {
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
        val queryArgs = new Bundle();
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?");
        queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{"1"});
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, ContactsContract.Contacts.SORT_KEY_PRIMARY);
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_LIMIT, String.valueOf(FILTER_QUERY_MAX_SIZE));
        // Create utility classes
        val extractor = new Extractors.IContactBriefEntity();
        // Make query and extract data
        try (val cursor = context.getContentResolver().query(uri, PROJECTION_CONTACT, queryArgs, null)) {
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
    public @Nullable ContactEntity getByLookupKey(@NonNull String lookupKey) {
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.i(TAG, "Permission READ_CONTACTS not granted");
            return null;
        }
        // Extract main contact
        var uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        var queryArgs = new Bundle();
        // Create utility classes
        val extractor = new Extractors.IContactBriefEntity();
        // Make query and extract data
        ContactBriefEntity contactBriefEntity;
        try (val cursor = context.getContentResolver().query(uri, PROJECTION_CONTACT, queryArgs, null)) {
            if (cursor != null && cursor.moveToNext()) {
                contactBriefEntity = extractor.apply(cursor);
            } else {
                Log.i(TAG, "Contact with lookup key " + lookupKey + " does not exists");
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "Cannot execute getByLookupKey::contactBriefEntity query", e);
            return null;
        }
        // Extract phone numbers and email addresses
        uri = ContactsContract.Data.CONTENT_URI;
        queryArgs = new Bundle();
        queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, ContactsContract.Data.LOOKUP_KEY + " = ?");  // TODO: Optimize selecting only specific mime types
        queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, new String[]{lookupKey});
        // Create utility classes
        val phoneNumberExtractor = new Extractors.IPhoneNumberEntity();
        val emailAddressExtractor = new Extractors.IEmailAddressEntity();
        val photoExtractor = new Extractors.IPhotoEntity();
        // Make query and extract data
        var photoEntity = new PhotoEntity(null);
        val phoneNumberList = new ArrayList<PhoneNumberEntity>();
        val emailAddressList = new ArrayList<EmailAddressEntity>();
        try (val cursor = context.getContentResolver().query(uri, PROJECTION_DATA, queryArgs, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String mimeType = cursor.getString(0);
                    if (Objects.equals(mimeType, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                        photoEntity = photoExtractor.apply(cursor);
                    } else if (Objects.equals(mimeType, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                        phoneNumberList.add(phoneNumberExtractor.apply(cursor));
                    } else if (Objects.equals(mimeType, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                        emailAddressList.add(emailAddressExtractor.apply(cursor));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Cannot execute getByLookupKey::phoneNumberAndEmailAddressEntity query", e);
            return null;
        }
        // Return the contact
        return new ContactEntity(contactBriefEntity, phoneNumberList, emailAddressList, photoEntity);
    }

    @Override
    public boolean setStarred(@NonNull String lookupKey, boolean starred) {
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.WRITE_CONTACTS)) {
            Log.i(TAG, "Permission WRITE_CONTACTS not granted");
            return false;
        }
        // Extract main data
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        val contentValues = new ContentValues();
        contentValues.put(ContactsContract.Contacts.STARRED, starred ? 1 : 0);
        val queryArgs = new Bundle();
        // Update
        return context.getContentResolver().update(uri, contentValues, queryArgs) != 0;
    }

    @Override
    public boolean delete(@NonNull String lookupKey) {
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.WRITE_CONTACTS)) {
            Log.i(TAG, "Permission WRITE_CONTACTS not granted");
            return false;
        }
        // Extract main data
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        val queryArgs = new Bundle();
        // Delete
        return context.getContentResolver().delete(uri, queryArgs) != 0;
    }

}
