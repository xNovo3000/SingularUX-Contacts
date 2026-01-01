package org.singularux.contacts.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class ComponentDataDiffCallback extends DiffUtil.ItemCallback<ComponentData> {

    @Override
    public boolean areItemsTheSame(@NonNull ComponentData oldItem,
                                   @NonNull ComponentData newItem
    ) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull ComponentData oldItem,
                                      @NonNull ComponentData newItem
    ) {
        return Objects.equals(oldItem, newItem);
    }

}
