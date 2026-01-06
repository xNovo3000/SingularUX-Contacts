package org.singularux.contacts.feature.contactview.presentation;

import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface ContactViewViewModelFactory {
    ContactViewViewModel create(String lookupKey);
}
