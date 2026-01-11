package org.singularux.contacts.feature.contactlist.ui.behavior;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.selection.SelectionTracker;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.search.SearchBar;

import org.jspecify.annotations.NonNull;
import org.singularux.contacts.feature.contactlist.R;
import org.singularux.contacts.feature.contactlist.ui.util.ContactListStatusBarScrim;

import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class TopBarUpdateSelectionObserver extends SelectionTracker.SelectionObserver<Long> {

    private final Set<Long> selectedKeys = new HashSet<>();

    private final Context context;

    private final SelectionTracker<Long> selectionTracker;

    private final ContactListStatusBarScrim contactListStatusBarScrim;
    private final SearchBar defaultSearchBar;
    private final MaterialToolbar selectionToolbar;

    @Override
    public void onItemStateChanged(@NonNull Long key, boolean selected) {
        if (selected)
            selectedKeys.add(key);
        else
            selectedKeys.remove(key);
    }

    @Override
    public void onSelectionChanged() {
        if (selectedKeys.isEmpty()) {
            defaultSearchBar.setVisibility(View.VISIBLE);
            defaultSearchBar.setEnabled(true);
            selectionToolbar.setVisibility(View.GONE);
            selectionToolbar.setEnabled(false);
            contactListStatusBarScrim.onSelectionEnd();
        } else {
            defaultSearchBar.setVisibility(View.GONE);
            defaultSearchBar.setEnabled(false);
            selectionToolbar.setVisibility(View.VISIBLE);
            selectionToolbar.setEnabled(true);
            contactListStatusBarScrim.onSelectionBegin();
            // Update toolbar title
            val title = context.getString(R.string.contact_list_selection_title,
                    selectedKeys.size());
            selectionToolbar.setTitle(title);
        }
    }

    @Override
    public void onSelectionRestored() {
        selectedKeys.clear();
        for (val key : selectionTracker.getSelection()) {
            selectedKeys.add(key);
        }
        onSelectionChanged();
    }

}
