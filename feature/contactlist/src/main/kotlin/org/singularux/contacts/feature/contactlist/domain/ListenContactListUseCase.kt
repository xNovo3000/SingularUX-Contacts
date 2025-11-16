package org.singularux.contacts.feature.contactlist.domain

import org.singularux.contacts.data.contacts.repository.ContactsRepository
import javax.inject.Inject

class ListenContactListUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {

    operator fun invoke() {

    }

}