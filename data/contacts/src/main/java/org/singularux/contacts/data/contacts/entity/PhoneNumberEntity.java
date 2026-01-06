package org.singularux.contacts.data.contacts.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class PhoneNumberEntity {
    @NonNull String number;
    @NonNull PhoneNumberLabel label;
    @Nullable String customLabel;
}
