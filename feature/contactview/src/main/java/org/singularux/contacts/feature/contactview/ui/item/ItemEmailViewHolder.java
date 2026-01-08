package org.singularux.contacts.feature.contactview.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactview.R;
import org.singularux.contacts.feature.contactview.databinding.ItemEmailBinding;

import lombok.val;

public class ItemEmailViewHolder extends RecyclerView.ViewHolder {

    public final MaterialTextView emailAddressTextView, labelTextView;
    public final MaterialButton sendEmailButton;

    public ItemEmailViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ItemEmailBinding.bind(itemView);
        this.emailAddressTextView = binding.itemEmailAddress;
        this.labelTextView = binding.itemEmailLabel;
        this.sendEmailButton = binding.itemEmailActionSendEmail;
    }

    public static @NonNull ItemEmailViewHolder create(@NonNull ViewGroup parent) {
        return new ItemEmailViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_email, parent, false));
    }

}
