package org.singularux.contacts.feature.contactlist.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactlist.R;
import org.singularux.contacts.feature.contactlist.databinding.ItemHeaderBinding;

import lombok.val;

public class ComponentHeaderViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_ID = 100;

    public final MaterialTextView headline;

    public ComponentHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ItemHeaderBinding.bind(itemView);
        this.headline = binding.itemHeaderHeadline;
    }

    public void onBindViewHolder(@NonNull ComponentHeaderData data) {
        // If there is a labelRes, then should be put in the header
        // Use the label otherwise
        if (data.getLabelRes() != null) {
            val context = itemView.getContext();
            val label = context.getString(data.getLabelRes());
            headline.setText(label);
        } else if (data.getLabel() != null) {
            headline.setText(data.getLabel());
        } else {
            throw new RuntimeException("Both labelRes and label are null");
        }
    }

    public static @NonNull ComponentHeaderViewHolder create(@NonNull ViewGroup parent) {
        return new ComponentHeaderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false));
    }

}
