package org.singularux.contacts.feature.contactdetail.presentation

import dagger.assisted.AssistedFactory

@AssistedFactory
interface ContactDetailViewModelFactory {
    fun create(lookupKey: String): ContactDetailViewModel
}