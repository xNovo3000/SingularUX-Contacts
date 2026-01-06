package org.singularux.contacts.feature.contactlist.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.singularux.contacts.core.threading.BackgroundExecutorService;
import org.singularux.contacts.core.threading.IOScheduler;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactViewHolder;
import org.singularux.contacts.feature.contactlist.ui.item.ItemData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemDataDiffCallback;
import org.singularux.contacts.feature.contactlist.ui.item.ItemHeaderData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemHeaderViewHolder;
import org.singularux.contacts.feature.contactlist.ui.util.ContactThumbnailCache;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import lombok.val;

public class ContactListRecyclerViewAdapter
        extends ListAdapter<ItemData, RecyclerView.ViewHolder> {

    private static final int INVALID_VIEW_TYPE_ID = -1;

    private final Scheduler ioScheduler;
    private final ContactThumbnailCache contactThumbnailCache;

    @Inject
    public ContactListRecyclerViewAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService,
            @IOScheduler Scheduler ioScheduler,
            ContactThumbnailCache contactThumbnailCache
    ) {
        super(new AsyncDifferConfig.Builder<>(new ItemDataDiffCallback())
                .setBackgroundThreadExecutor(backgroundExecutorService)
                .build());
        this.ioScheduler = ioScheduler;
        this.contactThumbnailCache = contactThumbnailCache;
        setHasStableIds(true);
    }

    @Override
    public @NonNull RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        switch (viewType) {
            case ItemHeaderViewHolder.VIEW_TYPE_ID:
                return ItemHeaderViewHolder.create(parent);
            case ItemContactViewHolder.VIEW_TYPE_ID:
                return ItemContactViewHolder.create(parent);
            default:
                throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        val currentItem = getItem(position);
        if (holder instanceof ItemHeaderViewHolder) {
            ((ItemHeaderViewHolder) holder)
                    .onBindViewHolder((ItemHeaderData) currentItem);
        } else if (holder instanceof ItemContactViewHolder) {
            ((ItemContactViewHolder) holder)
                    .onBindViewHolder((ItemContactData) currentItem, ioScheduler, contactThumbnailCache);
        } else {
            throw new IllegalArgumentException("Invalid holder class: " + holder.getClass());
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ItemContactViewHolder) {
            ((ItemContactViewHolder) holder).onViewRecycled();
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        val currentItem = getItem(position);
        if (currentItem instanceof ItemContactData) {
            return ItemContactViewHolder.VIEW_TYPE_ID;
        } else if (currentItem instanceof ItemHeaderData) {
            return ItemHeaderViewHolder.VIEW_TYPE_ID;
        }
        return INVALID_VIEW_TYPE_ID;
    }

}
