package org.singularux.contacts.feature.contactlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import org.singularux.contacts.feature.contactlist.domain.ListenContactListUseCase
import org.singularux.contacts.feature.contactlist.model.ContactItem
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val listenContactListUseCase: ListenContactListUseCase
) : ViewModel() {

    val contactListFlow = listenContactListUseCase()
        .map { contactBriefEntityList ->
            withContext(Dispatchers.Default) {
                contactBriefEntityList.map { contactBriefEntity ->
                    ContactItem(
                        lookupKey = contactBriefEntity.lookupKey,
                        displayName = contactBriefEntity.displayName,
                        thumbnailUri = contactBriefEntity.thumbnailUri
                    )
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}