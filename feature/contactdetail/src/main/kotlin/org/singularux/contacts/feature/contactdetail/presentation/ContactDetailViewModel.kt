package org.singularux.contacts.feature.contactdetail.presentation

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel(assistedFactory = ContactDetailViewModelFactory::class)
class ContactDetailViewModel @AssistedInject constructor(
    @Assisted lookupKey: String
) : ViewModel()