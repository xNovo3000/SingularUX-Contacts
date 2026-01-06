package org.singularux.contacts.feature.contactlist.ui.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ComponentContactDataDiffCallback extends DiffUtil.ItemCallback<ComponentContactData> {

    @Override
    public boolean areItemsTheSame(@NonNull ComponentContactData oldItem,
                                   @NonNull ComponentContactData newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ComponentContactData oldItem,
                                      @NonNull ComponentContactData newItem) {
        return newItem.equals(oldItem);
    }

}
