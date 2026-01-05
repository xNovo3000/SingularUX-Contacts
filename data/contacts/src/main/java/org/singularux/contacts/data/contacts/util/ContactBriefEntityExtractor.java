package org.singularux.contacts.data.contacts.util;

import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;

import java.util.function.Function;

public class ContactBriefEntityExtractor implements Function<Cursor, ContactBriefEntity> {

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
