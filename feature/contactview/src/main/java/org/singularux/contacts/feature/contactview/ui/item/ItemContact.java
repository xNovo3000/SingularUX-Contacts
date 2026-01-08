package org.singularux.contacts.feature.contactview.ui.item;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import lombok.Value;

@Value
public class ItemContact {
    @NonNull String displayName;
    @Nullable Uri photoPath;
    boolean starred;
    boolean userProfile;
    @NonNull List<ItemEmailData> emailAddressList;
    @NonNull List<ItemPhoneData> phoneNumbersList;
}
