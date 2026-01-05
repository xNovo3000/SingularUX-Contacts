package org.singularux.contacts.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;

import org.singularux.contacts.core.BackgroundExecutorService;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class ContactListSearchRecyclerViewAdapter
        extends ListAdapter<ComponentContactData, ComponentContactViewHolder> {

    private static final int INVALID_VIEW_TYPE_ID = -1;

    private final ContactThumbnailCache contactThumbnailCache;

    @Inject
    public ContactListSearchRecyclerViewAdapter(
            @BackgroundExecutorService ExecutorService backgroundExecutorService,
            ContactThumbnailCache contactThumbnailCache
    ) {
        super(new AsyncDifferConfig.Builder<>(new ComponentContactDataDiffCallback())
                .setBackgroundThreadExecutor(backgroundExecutorService)
                .build());
        this.contactThumbnailCache = contactThumbnailCache;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ComponentContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentContactViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

}
