package org.singularux.contacts.feature.contactlist.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import org.singularux.contacts.feature.contactlist.domain.GetContactListByNameLikeUseCase
import org.singularux.contacts.feature.contactlist.domain.GetContactListPermissionsUseCase
import org.singularux.contacts.feature.contactlist.domain.ListenContactListUseCase
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
@OptIn(FlowPreview::class)
class ContactListViewModel @Inject constructor(
    getContactListByNameLikeUseCase: GetContactListByNameLikeUseCase,
    getContactListPermissionsUseCase: GetContactListPermissionsUseCase,
    listenContactListUseCase: ListenContactListUseCase
) : ViewModel() {

    val contactListPermissions = getContactListPermissionsUseCase()

    val contactListData = listenContactListUseCase()
        .map { withContext(Dispatchers.Default) { fromContactBriefEntityListToUiList(it) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val searchBarTextFieldState = TextFieldState()
    val searchContactListData = snapshotFlow { searchBarTextFieldState.text }
        .debounce(150.milliseconds)
        .map { withContext(Dispatchers.IO) { getContactListByNameLikeUseCase(it.toString()) } }
        .map { withContext(Dispatchers.Default) { fromContactBriefEntityListToUiList(it) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}