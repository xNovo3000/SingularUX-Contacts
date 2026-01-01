package org.singularux.contacts.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.databinding.ComponentHeaderBinding;

import lombok.val;

public class ComponentHeaderViewHolder extends RecyclerView.ViewHolder {

    public final MaterialTextView headline;

    public ComponentHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ComponentHeaderBinding.bind(itemView);
        this.headline = binding.componentHeaderHeadline;
    }

}
