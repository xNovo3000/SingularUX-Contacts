package org.singularux.contacts.feature.contactlist.ui.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ItemContactDataDiffCallback extends DiffUtil.ItemCallback<ItemContactData> {

    @Override
    public boolean areItemsTheSame(@NonNull ItemContactData oldItem,
                                   @NonNull ItemContactData newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemContactData oldItem,
                                      @NonNull ItemContactData newItem) {
        return newItem.equals(oldItem);
    }

}
