package org.singularux.contacts.feature.contactview.ui.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class ItemPhoneData {
    @NonNull String phoneNumber;
    @Nullable ItemPhoneLabel label;
    @Nullable String customLabel;
}
