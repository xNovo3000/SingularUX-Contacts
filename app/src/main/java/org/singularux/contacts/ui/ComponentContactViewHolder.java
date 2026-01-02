package org.singularux.contacts.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.databinding.ComponentContactBinding;

import lombok.val;

public class ComponentContactViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_ID = 101;

    public final ShapeableImageView avatarImage;
    public final MaterialTextView avatarText, headline;

    public ComponentContactViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ComponentContactBinding.bind(itemView);
        this.avatarImage = binding.componentContactAvatarImage;
        this.avatarText = binding.componentContactAvatarText;
        this.headline = binding.componentContactHeadline;
    }

}
