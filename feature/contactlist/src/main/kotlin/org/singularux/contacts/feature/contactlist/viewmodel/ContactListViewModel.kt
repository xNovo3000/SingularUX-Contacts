package org.singularux.contacts.feature.contactlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import org.singularux.contacts.feature.contactlist.domain.GetAllContactsUseCase
import org.singularux.contacts.feature.contactlist.model.ContactBriefItem
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    getAllContactsUseCase: GetAllContactsUseCase
) : ViewModel() {

    val contactListWithDecorators = getAllContactsUseCase()
        .map { contactBriefList ->
            // TODO: Modify here
            withContext(Dispatchers.Default) {
                contactBriefList.map { contactBrief ->
                    ContactBriefItem.Contact(
                        lookupKey = contactBrief.lookupKey,
                        displayName = contactBrief.displayName,
                        thumbnailUri = contactBrief.thumbnailUri
                    )
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

}