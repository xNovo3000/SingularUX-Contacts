package org.singularux.contacts.feature.contactview.presentation;

import androidx.lifecycle.ViewModel;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel(assistedFactory = ContactViewViewModelFactory.class)
public class ContactViewViewModel extends ViewModel {

    @AssistedInject
    public ContactViewViewModel(@Assisted String lookupKey) {
    }

}
