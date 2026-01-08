package org.singularux.contacts.feature.contactview.ui.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ItemEmailDataDiffCallback extends DiffUtil.ItemCallback<ItemEmailData> {

    @Override
    public boolean areItemsTheSame(@NonNull ItemEmailData oldItem,
                                   @NonNull ItemEmailData newItem) {
        return newItem.getEmailAddress().equals(oldItem.getEmailAddress());
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemEmailData oldItem,
                                      @NonNull ItemEmailData newItem) {
        return newItem.equals(oldItem);
    }

}
