package org.singularux.contacts.data.contacts.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class EmailAddressEntity {
    @NonNull String address;
    @NonNull EmailAddressLabel label;
    @Nullable String customLabel;
}
