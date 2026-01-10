package org.singularux.contacts.feature.contactlist.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactlist.R;
import org.singularux.contacts.feature.contactlist.databinding.ItemHeaderBinding;

import lombok.val;

public class ItemHeaderViewHolder extends ItemViewHolder {

    public static final int VIEW_TYPE_ID = 100;

    public final MaterialTextView headline;

    public ItemHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ItemHeaderBinding.bind(itemView);
        this.headline = binding.itemHeaderHeadline;
    }

    public void onBindViewHolder(@NonNull ItemHeaderData data) {
        // If there is a labelRes, then should be put in the header
        // Use the label otherwise
        if (data.getStandardLabel() != null) {
            val context = itemView.getContext();
            val label = context.getString(data.getStandardLabel().labelRes);
            headline.setText(label);
        } else if (data.getLabel() != null) {
            headline.setText(data.getLabel());
        } else {
            throw new RuntimeException("Both standardLabel and label are null");
        }
    }

    public static @NonNull ItemHeaderViewHolder create(@NonNull ViewGroup parent) {
        return new ItemHeaderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false));
    }

}
