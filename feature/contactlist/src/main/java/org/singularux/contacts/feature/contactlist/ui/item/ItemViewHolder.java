package org.singularux.contacts.feature.contactlist.ui.item;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
        return new ItemDataDetails(getBindingAdapterPosition(), getItemId());
    }

}
