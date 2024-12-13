package org.singularux.contacts.feature.contactlist.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.singularux.contacts.feature.contactlist.domain.GetAllContactsUseCase
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val getAllContactsUseCase: GetAllContactsUseCase
) : ViewModel()