package org.singularux.contacts.feature.contactview.ui.behavior;

import android.view.MenuItem;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.singularux.contacts.feature.contactview.R;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnToolbarMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

    private final ComponentActivity componentActivity;
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
            // Create deletion alert dialog
            new MaterialAlertDialogBuilder(componentActivity)
                    .setTitle(R.string.delete_dialog_title)
                    .setMessage(R.string.delete_dialog_description)
                    .setPositiveButton(R.string.delete_dialog_action_positive, (d, w) -> viewModel.delete())
                    .setNegativeButton(R.string.delete_dialog_action_negative, (d, w) -> d.dismiss())
                    .show();
        }
        return true;
    }

}
