package org.singularux.contacts.feature.contactlist.ui.util;

import android.view.MotionEvent;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.singularux.contacts.feature.contactlist.ui.item.ItemViewHolder;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class StandardRecyclerViewItemDetailsLookup extends ItemDetailsLookup<Long> {

    private final RecyclerView recyclerView;

    @Override
    public @Nullable ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        val view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof ItemViewHolder) {
                return ((ItemViewHolder) viewHolder).getItemDetails();
            }
        }
        return null;
    }

}
