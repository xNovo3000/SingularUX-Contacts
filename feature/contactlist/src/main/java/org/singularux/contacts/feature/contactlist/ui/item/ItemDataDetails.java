package org.singularux.contacts.feature.contactlist.ui.item;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemDataDetails extends ItemDetailsLookup.ItemDetails<Long> {

    private final int bindingAdapterPosition;
    private final long id;

    @Override
    public int getPosition() {
        return bindingAdapterPosition;
    }

    @Override
    public @Nullable Long getSelectionKey() {
        return id;
    }

}
