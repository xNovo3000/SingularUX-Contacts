package org.singularux.contacts.feature.contactlist.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;

import org.singularux.contacts.core.threading.BackgroundExecutorService;
import org.singularux.contacts.core.threading.IOScheduler;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactDataDiffCallback;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactViewHolder;
import org.singularux.contacts.feature.contactlist.ui.util.ContactThumbnailCache;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.rxjava3.core.Scheduler;

@ActivityScoped
public class ContactListSearchRecyclerViewAdapter
        extends ListAdapter<ItemContactData, ItemContactViewHolder> {

    private final Scheduler ioScheduler;
    private final ContactThumbnailCache contactThumbnailCache;

    @Inject
    public ContactListSearchRecyclerViewAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService,
            @IOScheduler Scheduler ioScheduler,
            ContactThumbnailCache contactThumbnailCache
    ) {
        super(new AsyncDifferConfig.Builder<>(new ItemContactDataDiffCallback())
                .setBackgroundThreadExecutor(backgroundExecutorService)
                .build());
        this.ioScheduler = ioScheduler;
        this.contactThumbnailCache = contactThumbnailCache;
        setHasStableIds(true);
    }

    @Override
    public @NonNull ItemContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        return ItemContactViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemContactViewHolder holder, int position) {
        holder.onBindViewHolder(getItem(position), ioScheduler, contactThumbnailCache, null);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

}
