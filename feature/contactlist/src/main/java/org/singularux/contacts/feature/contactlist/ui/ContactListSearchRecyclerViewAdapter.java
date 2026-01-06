package org.singularux.contacts.feature.contactlist.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;

import org.singularux.contacts.core.threading.BackgroundExecutorService;
import org.singularux.contacts.core.threading.IOScheduler;
import org.singularux.contacts.feature.contactlist.ui.item.ComponentContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ComponentContactDataDiffCallback;
import org.singularux.contacts.feature.contactlist.ui.item.ComponentContactViewHolder;
import org.singularux.contacts.feature.contactlist.ui.util.ContactThumbnailCache;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;

public class ContactListSearchRecyclerViewAdapter
        extends ListAdapter<ComponentContactData, ComponentContactViewHolder> {

    private final Scheduler ioScheduler;
    private final ContactThumbnailCache contactThumbnailCache;

    @Inject
    public ContactListSearchRecyclerViewAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService,
            @IOScheduler Scheduler ioScheduler,
            ContactThumbnailCache contactThumbnailCache
    ) {
        super(new AsyncDifferConfig.Builder<>(new ComponentContactDataDiffCallback())
                .setBackgroundThreadExecutor(backgroundExecutorService)
                .build());
        this.ioScheduler = ioScheduler;
        this.contactThumbnailCache = contactThumbnailCache;
        setHasStableIds(true);
    }

    @Override
    public @NonNull ComponentContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
        return ComponentContactViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentContactViewHolder holder, int position) {
        holder.onBindViewHolder(getItem(position), ioScheduler, contactThumbnailCache);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

}
