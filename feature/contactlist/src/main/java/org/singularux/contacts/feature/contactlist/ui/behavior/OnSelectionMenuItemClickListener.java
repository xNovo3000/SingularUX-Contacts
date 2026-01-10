package org.singularux.contacts.feature.contactlist.ui.behavior;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import org.singularux.contacts.feature.contactlist.R;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnSelectionMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.contact_list_selection_share) {
            // TODO: Create and launch share intent
        } else if (item.getItemId() == R.id.contact_list_selection_delete) {
            // TODO: Open deletion dialog
        } else if (item.getItemId() == R.id.contact_list_selection_select_all) {
            // TODO: Select all elements
        }
        // Event is always handled
        return true;
    }

}
