package org.singularux.contacts.feature.contactlist.ui.item;

import android.net.Uri;

import androidx.annotation.NonNull;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ItemContactData extends ItemData {

    public static final long DATA_TYPE_ID = 101L << 32;

    @NonNull String lookupKey;
    @NonNull String displayName;
    Uri thumbnailPath;

    @Override
    public long getId() {
        return DATA_TYPE_ID | (lookupKey.hashCode() & 0xFFFFFFFFL);
    }

}
