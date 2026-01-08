package org.singularux.contacts.feature.contactview.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;

import org.singularux.contacts.core.threading.BackgroundExecutorService;
import org.singularux.contacts.feature.contactview.ui.behavior.OnSendEmailClickListener;
import org.singularux.contacts.feature.contactview.ui.item.ItemEmailData;
import org.singularux.contacts.feature.contactview.ui.item.ItemEmailDataDiffCallback;
import org.singularux.contacts.feature.contactview.ui.item.ItemEmailViewHolder;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import lombok.val;

public class EmailAddressListAdapter extends ListAdapter<ItemEmailData, ItemEmailViewHolder> {

    @Inject
    public EmailAddressListAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService) {
        super(new AsyncDifferConfig.Builder<>(new ItemEmailDataDiffCallback())
                .setBackgroundThreadExecutor(backgroundExecutorService)
                .build());
        setHasStableIds(true);
    }

    @Override
    public @NonNull ItemEmailViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
        return ItemEmailViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemEmailViewHolder holder, int position) {
        val currentItem = getItem(position);
        // Set email address
        holder.emailAddressTextView.setText(currentItem.getEmailAddress());
        // Set label
        if (currentItem.getLabel() != null) {
            val context = holder.itemView.getContext();
            val labelText = context.getString(currentItem.getLabel().labelRes);
            holder.labelTextView.setText(labelText);
        } else {
            holder.labelTextView.setText(currentItem.getCustomLabel());
        }
        // Set call and message click listeners
        holder.sendEmailButton.setOnClickListener(
                new OnSendEmailClickListener(currentItem.getEmailAddress()));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getEmailAddress().hashCode();
    }

}
