package org.singularux.contacts.feature.contactlist.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.singularux.contacts.feature.contactlist.domain.GetContactListPermissionsUseCase
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    getContactListPermissionsUseCase: GetContactListPermissionsUseCase
) : ViewModel() {

    val searchBarTextFieldState = TextFieldState()

    val contactListPermissions = getContactListPermissionsUseCase()

}