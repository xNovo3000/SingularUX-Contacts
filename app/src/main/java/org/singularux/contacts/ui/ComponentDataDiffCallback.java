package org.singularux.contacts.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class ComponentDataDiffCallback extends DiffUtil.ItemCallback<ComponentData> {

    @Override
    public boolean areItemsTheSame(@NonNull ComponentData oldItem,
                                   @NonNull ComponentData newItem
    ) {
        return oldItem.getClass() == newItem.getClass() && oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ComponentData oldItem,
                                      @NonNull ComponentData newItem
    ) {
        return newItem.equals(oldItem);
    }

}
