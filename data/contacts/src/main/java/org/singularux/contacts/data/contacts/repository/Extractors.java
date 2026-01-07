package org.singularux.contacts.data.contacts.repository;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.data.contacts.entity.EmailAddressEntity;
import org.singularux.contacts.data.contacts.entity.EmailAddressLabel;
import org.singularux.contacts.data.contacts.entity.PhoneNumberEntity;
import org.singularux.contacts.data.contacts.entity.PhoneNumberLabel;
import org.singularux.contacts.data.contacts.entity.PhotoEntity;

import java.util.function.Function;

class Extractors {

    private Extractors() {}

    public static class IContactBriefEntity implements Function<Cursor, ContactBriefEntity> {

        @Override
        public @NonNull ContactBriefEntity apply(@NonNull Cursor cursor) {
            // Get lookupKey
            String lookupKey = cursor.getString(0);
            // Get displayName
            String displayName = cursor.getString(1);
            // Get thumbnailPath (maybe)
            Uri maybeThumbnailPath = null;
            if (!cursor.isNull(2)) {
                maybeThumbnailPath = Uri.parse(cursor.getString(2));
            }
            // Get starred
            boolean starred = cursor.getInt(3) != 0;
            // Return new object
            return new ContactBriefEntity(lookupKey, displayName, maybeThumbnailPath, starred);
        }

    }

    public static class IPhoneNumberEntity implements Function<Cursor, PhoneNumberEntity> {

        @Override
        public @NonNull PhoneNumberEntity apply(@NonNull Cursor cursor) {
            // Get phone number
            String number = cursor.getString(1);
            // Get label
            PhoneNumberLabel label = PhoneNumberLabel.CUSTOM;
            switch (cursor.getInt(2)) {
                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                    label = PhoneNumberLabel.HOME;
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                    label = PhoneNumberLabel.MOBILE;
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                    label = PhoneNumberLabel.WORK;
                    break;
            }
            // Get custom label (if present)
            String customLabel = null;
            if (!cursor.isNull(3)) {
                customLabel = cursor.getString(3);
            }
            // Return entity
            return new PhoneNumberEntity(number, label, customLabel);
        }

    }

    public static class IEmailAddressEntity implements Function<Cursor, EmailAddressEntity> {

        @Override
        public @NonNull EmailAddressEntity apply(@NonNull Cursor cursor) {
            // Get email address
            String address = cursor.getString(1);
            // Get label
            EmailAddressLabel label = EmailAddressLabel.CUSTOM;
            switch (cursor.getInt(2)) {
                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                    label = EmailAddressLabel.HOME;
                    break;
                case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                    label = EmailAddressLabel.MOBILE;
                    break;
                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                    label = EmailAddressLabel.WORK;
                    break;
            }
            // Get custom label (if present)
            String customLabel = null;
            if (!cursor.isNull(3)) {
                customLabel = cursor.getString(3);
            }
            // Return entity
            return new EmailAddressEntity(address, label, customLabel);
        }

    }

    public static class IPhotoEntity implements Function<Cursor, PhotoEntity> {

        @Override
        public @NonNull PhotoEntity apply(@NonNull Cursor cursor) {
            // Get photo path from photo id (if present)
            Uri photoPath = null;
            if (!cursor.isNull(4)) {
                photoPath = ContentUris.withAppendedId(ContactsContract.DisplayPhoto.CONTENT_URI,
                        cursor.getInt(4));
            }
            // Return entity
            return new PhotoEntity(photoPath);
        }

    }

}
