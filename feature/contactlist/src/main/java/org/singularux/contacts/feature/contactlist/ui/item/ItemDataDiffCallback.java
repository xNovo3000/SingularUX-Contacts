package org.singularux.contacts.feature.contactlist.ui.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ItemDataDiffCallback extends DiffUtil.ItemCallback<ItemData> {

    @Override
    public boolean areItemsTheSame(@NonNull ItemData oldItem,
                                   @NonNull ItemData newItem
    ) {
        return oldItem.getClass() == newItem.getClass() && oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemData oldItem,
                                      @NonNull ItemData newItem
    ) {
        return newItem.equals(oldItem);
    }

}
