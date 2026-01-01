package org.singularux.contacts.ui;

import android.net.Uri;

import androidx.annotation.NonNull;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ComponentContactData extends ComponentData {

    @NonNull String lookupKey;
    @NonNull String displayName;
    Uri thumbnailPath;

    @Override
    public int getId() {
        return lookupKey.hashCode();
    }

}
