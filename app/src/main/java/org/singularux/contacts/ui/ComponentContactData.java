package org.singularux.contacts.ui;

import android.net.Uri;

import androidx.annotation.NonNull;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ComponentContactData extends ComponentData {

    public static final long DATA_TYPE_ID = 101L << 32;

    @NonNull String lookupKey;
    @NonNull String displayName;
    Uri thumbnailPath;

    @Override
    public long getId() {
        return DATA_TYPE_ID | (lookupKey.hashCode() & 0xFFFFFFFFL);
    }

}
