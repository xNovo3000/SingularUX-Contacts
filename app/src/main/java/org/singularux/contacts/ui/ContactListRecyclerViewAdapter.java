package org.singularux.contacts.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import lombok.val;

public class ContactListRecyclerViewAdapter
        extends ListAdapter<ComponentData, RecyclerView.ViewHolder> {

    public ContactListRecyclerViewAdapter() {
        super(new ComponentDataDiffCallback());
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        val currentItem = getItem(position);
        int classHashCode = currentItem.getClass().hashCode();
        int id = currentItem.getId();
        return (((long) classHashCode) << 32) | (id & 0xFFFFFFFFL);
    }

    @Override
    public int getItemViewType(int position) {
        val currentItem = getItem(position);
        return super.getItemViewType(position);
    }

}
