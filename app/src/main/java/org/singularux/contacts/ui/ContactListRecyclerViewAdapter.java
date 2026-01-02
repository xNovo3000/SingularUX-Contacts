package org.singularux.contacts.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.singularux.contacts.R;
import org.singularux.contacts.core.BackgroundExecutorService;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import lombok.val;

public class ContactListRecyclerViewAdapter
        extends ListAdapter<ComponentData, RecyclerView.ViewHolder> {

    public static final int INVALID_VIEW_TYPE_ID = -1;

    @Inject
    public ContactListRecyclerViewAdapter(
            @BackgroundExecutorService ExecutorService executorService
    ) {
        super(new AsyncDifferConfig.Builder<>(new ComponentDataDiffCallback())
                .setBackgroundThreadExecutor(executorService)
                .build());
        setHasStableIds(true);
    }

    @Override
    public @NonNull RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        switch (viewType) {
            case ComponentHeaderViewHolder.VIEW_TYPE_ID:
                return new ComponentHeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.component_header, parent, false));
            case ComponentContactViewHolder.VIEW_TYPE_ID:
                return new ComponentContactViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.component_header, parent, false));
            default:
                throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        val currentItem = getItem(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ComponentHeaderViewHolder.VIEW_TYPE_ID:
                assert currentItem instanceof ComponentHeaderData;
                onBindComponentHeaderViewHolder((ComponentHeaderData) currentItem,
                        (ComponentHeaderViewHolder) holder);
            case ComponentContactViewHolder.VIEW_TYPE_ID:
                assert currentItem instanceof ComponentContactData;
                onBindComponentContactViewHolder((ComponentContactData) currentItem,
                        (ComponentContactViewHolder) holder);
            default:
                throw new IllegalArgumentException("Invalid viewType: " + viewType);
        }
    }

    private void onBindComponentHeaderViewHolder(@NonNull ComponentHeaderData data,
                                                 @NonNull ComponentHeaderViewHolder holder
    ) {
        if (data.getLabelRes() != null) {
            val context = ContextCompat.getContextForLanguage(holder.itemView.getContext());
            val label = context.getString(data.getLabelRes());
            holder.headline.setText(label);
        } else if (data.getLabel() != null) {
            holder.headline.setText(data.getLabel());
        } else {
            throw new RuntimeException("Both labelRes and label are null");
        }
    }

    private void onBindComponentContactViewHolder(@NonNull ComponentContactData data,
                                                  @NonNull ComponentContactViewHolder holder
    ) {

        // TODO: Load avatar
    }

    @Override
    public long getItemId(int position) {
        val currentItem = getItem(position);
        int classHashCode = currentItem.getClass().hashCode();
        int id = currentItem.getId();
        return (((long) classHashCode) << 32) | (id & 0xFFFFFFFFL);
    }

    @Override
    public int getItemViewType(int position) {
        val currentItem = getItem(position);
        if (currentItem.getClass() == ComponentContactData.class) {
            return ComponentContactViewHolder.VIEW_TYPE_ID;
        } else if (currentItem.getClass() == ComponentHeaderData.class) {
            return ComponentHeaderViewHolder.VIEW_TYPE_ID;
        }
        return INVALID_VIEW_TYPE_ID;
    }

}
