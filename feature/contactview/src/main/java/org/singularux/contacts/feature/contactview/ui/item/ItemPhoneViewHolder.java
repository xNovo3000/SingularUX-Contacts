package org.singularux.contacts.feature.contactview.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactview.R;
import org.singularux.contacts.feature.contactview.databinding.ItemPhoneBinding;

import lombok.val;

public class ItemPhoneViewHolder extends RecyclerView.ViewHolder {

    public final MaterialTextView phoneNumberTextView, labelTextView;
    public final MaterialButton callButton, messageButton;

    public ItemPhoneViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ItemPhoneBinding.bind(itemView);
        this.phoneNumberTextView = binding.itemPhoneNumber;
        this.labelTextView = binding.itemPhoneLabel;
        this.callButton = binding.itemPhoneActionCall;
        this.messageButton = binding.itemPhoneActionMessage;
    }

    public static @NonNull ItemPhoneViewHolder create(@NonNull ViewGroup parent) {
        return new ItemPhoneViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone, parent, false));
    }

}
