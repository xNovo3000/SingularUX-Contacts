package org.singularux.contacts.feature.contactview.ui.behavior;

import android.view.View;

import androidx.annotation.NonNull;

import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnAddToFavoritesClickListener implements View.OnClickListener {

    private final ContactViewViewModel viewModel;

    @Override
    public void onClick(@NonNull View v) {
        viewModel.addToFavorites();
    }

}
