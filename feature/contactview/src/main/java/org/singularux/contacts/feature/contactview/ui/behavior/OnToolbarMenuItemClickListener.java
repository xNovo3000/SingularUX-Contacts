package org.singularux.contacts.feature.contactview.ui.behavior;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import org.singularux.contacts.feature.contactview.R;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnToolbarMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

    private final ContactViewViewModel viewModel;

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.contact_view_toolbar_edit) {
            // TODO: Go to edit screen
        } else if (item.getItemId() == R.id.contact_view_toolbar_favorite_add) {
            viewModel.addToFavorites();
        } else if (item.getItemId() == R.id.contact_view_toolbar_favorite_remove) {
            viewModel.removeFromFavorites();
        } else if (item.getItemId() == R.id.contact_view_toolbar_delete) {
            // TODO: Create delete dialog
        }
        return true;
    }
    
}
