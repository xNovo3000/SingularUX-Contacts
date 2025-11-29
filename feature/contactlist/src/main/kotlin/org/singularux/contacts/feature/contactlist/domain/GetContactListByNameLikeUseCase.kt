package org.singularux.contacts.feature.contactlist.domain

import android.util.Log
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity
import org.singularux.contacts.data.contacts.repository.ContactsRepository
import javax.inject.Inject

class GetContactListByNameLikeUseCase @Inject constructor(
    private val contactsRepository: ContactsRepository
) {

    companion object {
        private const val TAG = "GetContactListByNameLikeUseCase"
        private const val LIMIT = 20
    }

    suspend operator fun invoke(query: String): List<ContactBriefEntity> {
        Log.d(TAG, "Querying $query with limit $LIMIT")
        return contactsRepository.getByDisplayNameLike(query, LIMIT)
    }

}