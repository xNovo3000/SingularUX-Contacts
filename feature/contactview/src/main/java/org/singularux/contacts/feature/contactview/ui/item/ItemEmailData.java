package org.singularux.contacts.feature.contactview.ui.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Value;

@Value
public class ItemEmailData {
    @NonNull String emailAddress;
    @Nullable ItemEmailLabel label;
    @Nullable String customLabel;
}
