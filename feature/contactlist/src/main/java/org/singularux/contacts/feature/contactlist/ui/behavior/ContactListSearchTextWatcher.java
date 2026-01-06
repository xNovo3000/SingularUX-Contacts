package org.singularux.contacts.feature.contactlist.ui.behavior;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.NonNull;

import org.singularux.contacts.feature.contactlist.presentation.ContactListViewModel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactListSearchTextWatcher implements TextWatcher {

    private static final String TAG = "ContactListSearchTextWatcher";

    private final ContactListViewModel contactListViewModel;

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
        Log.d(TAG, "Updating query: " + s);
        contactListViewModel.updateSearchQuery(s.toString());
    }

}
