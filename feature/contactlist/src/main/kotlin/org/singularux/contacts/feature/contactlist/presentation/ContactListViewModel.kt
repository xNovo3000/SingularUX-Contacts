package org.singularux.contacts.feature.contactlist.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.singularux.contacts.feature.contactlist.domain.GetContactListPermissionsUseCase
import org.singularux.contacts.feature.contactlist.domain.ListenContactListUseCase
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    getContactListPermissionsUseCase: GetContactListPermissionsUseCase,
    listenContactListUseCase: ListenContactListUseCase
) : ViewModel() {

    val searchBarTextFieldState = TextFieldState()

    val contactListPermissions = getContactListPermissionsUseCase()
    val contactListData = listenContactListUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}