package org.singularux.contacts.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.singularux.contacts.core.BackgroundExecutorService;
import org.singularux.contacts.core.IOScheduler;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import lombok.val;

public class ContactListRecyclerViewAdapter
        extends ListAdapter<ComponentData, RecyclerView.ViewHolder> {

    private static final int INVALID_VIEW_TYPE_ID = -1;

    private final Scheduler ioScheduler;
    private final ContactThumbnailCache contactThumbnailCache;

    @Inject
    public ContactListRecyclerViewAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService,
            @IOScheduler Scheduler ioScheduler,
            ContactThumbnailCache contactThumbnailCache
    ) {
        super(new AsyncDifferConfig.Builder<>(new ComponentDataDiffCallback())
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
            case ComponentHeaderViewHolder.VIEW_TYPE_ID:
                return ComponentHeaderViewHolder.create(parent);
            case ComponentContactViewHolder.VIEW_TYPE_ID:
                return ComponentContactViewHolder.create(parent);
            default:
                throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        val currentItem = getItem(position);
        if (holder instanceof ComponentHeaderViewHolder) {
            ((ComponentHeaderViewHolder) holder)
                    .onBindViewHolder((ComponentHeaderData) currentItem);
        } else if (holder instanceof ComponentContactViewHolder) {
            ((ComponentContactViewHolder) holder)
                    .onBindViewHolder((ComponentContactData) currentItem, ioScheduler, contactThumbnailCache);
        } else {
            throw new IllegalArgumentException("Invalid holder class: " + holder.getClass());
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof ComponentContactViewHolder) {
            ((ComponentContactViewHolder) holder).onViewRecycled();
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        val currentItem = getItem(position);
        if (currentItem instanceof ComponentContactData) {
            return ComponentContactViewHolder.VIEW_TYPE_ID;
        } else if (currentItem instanceof ComponentHeaderData) {
            return ComponentHeaderViewHolder.VIEW_TYPE_ID;
        }
        return INVALID_VIEW_TYPE_ID;
    }

}
