package org.singularux.contacts.data;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class ContactBriefEntity {
    @NonNull String lookupKey;
    @NonNull String displayName;
    @Nullable Uri thumbnailPath;
    boolean starred;
}
