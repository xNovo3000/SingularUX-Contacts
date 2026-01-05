package org.singularux.contacts.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactListFabHideOnScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "ContactListFabHideOnScrollListener";

    private final ExtendedFloatingActionButton extendedFloatingActionButton;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 12 && extendedFloatingActionButton.isExtended()) {
            Log.d(TAG, "Shrinking extended FAB");
            extendedFloatingActionButton.shrink();
        } else if (dy < -12 && !extendedFloatingActionButton.isExtended()) {
            Log.d(TAG, "Extending extended FAB");
            extendedFloatingActionButton.extend();
        }
    }

}
