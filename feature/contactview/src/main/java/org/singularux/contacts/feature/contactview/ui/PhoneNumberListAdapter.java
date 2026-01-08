package org.singularux.contacts.feature.contactview.ui;

import android.telephony.PhoneNumberUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;

import org.singularux.contacts.core.threading.BackgroundExecutorService;
import org.singularux.contacts.feature.contactview.ui.behavior.OnCallClickListener;
import org.singularux.contacts.feature.contactview.ui.behavior.OnMessageClickListener;
import org.singularux.contacts.feature.contactview.ui.item.ItemPhoneData;
import org.singularux.contacts.feature.contactview.ui.item.ItemPhoneDataDiffCallback;
import org.singularux.contacts.feature.contactview.ui.item.ItemPhoneViewHolder;

import java.util.Locale;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;
import lombok.val;

@ActivityRetainedScoped
public class PhoneNumberListAdapter extends ListAdapter<ItemPhoneData, ItemPhoneViewHolder> {

    @Inject
    public PhoneNumberListAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService) {
        super(new AsyncDifferConfig.Builder<>(new ItemPhoneDataDiffCallback())
                .setBackgroundThreadExecutor(backgroundExecutorService)
                .build());
        setHasStableIds(true);
    }

    @Override
    public @NonNull ItemPhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        return ItemPhoneViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemPhoneViewHolder holder, int position) {
        val currentItem = getItem(position);
        // Set phone number
        holder.phoneNumberTextView.setText(PhoneNumberUtils.formatNumber(
                currentItem.getPhoneNumber(), Locale.getDefault().getISO3Country()));
        // Set label
        if (currentItem.getLabel() != null) {
            val context = holder.itemView.getContext();
            val labelText = context.getString(currentItem.getLabel().labelRes);
            holder.labelTextView.setText(labelText);
        } else {
            holder.labelTextView.setText(currentItem.getCustomLabel());
        }
        // Set call and message click listeners
        holder.callButton.setOnClickListener(
                new OnCallClickListener(currentItem.getPhoneNumber()));
        holder.messageButton.setOnClickListener(
                new OnMessageClickListener(currentItem.getPhoneNumber()));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getPhoneNumber().hashCode();
    }

}
