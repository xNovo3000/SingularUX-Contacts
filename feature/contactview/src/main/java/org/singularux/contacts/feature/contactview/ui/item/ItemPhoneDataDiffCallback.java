package org.singularux.contacts.feature.contactview.ui.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ItemPhoneDataDiffCallback extends DiffUtil.ItemCallback<ItemPhoneData> {

    @Override
    public boolean areItemsTheSame(@NonNull ItemPhoneData oldItem,
                                   @NonNull ItemPhoneData newItem) {
        return newItem.getPhoneNumber().equals(oldItem.getPhoneNumber());
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemPhoneData oldItem,
                                      @NonNull ItemPhoneData newItem) {
        return newItem.equals(oldItem);
    }

}
