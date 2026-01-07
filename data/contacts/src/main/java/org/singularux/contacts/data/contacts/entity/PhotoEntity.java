package org.singularux.contacts.data.contacts.entity;

import android.net.Uri;

import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class PhotoEntity {
    @Nullable Uri photoPath;
}
