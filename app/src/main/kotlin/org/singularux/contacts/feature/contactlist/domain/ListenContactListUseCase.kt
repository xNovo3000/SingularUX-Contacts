package org.singularux.contacts.feature.contactlist.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity
import javax.inject.Inject

class ListenContactListUseCase @Inject constructor() {

    companion object {
        private const val TAG = "ListenContactListUseCase"
    }

    operator fun invoke(): Flow<List<ContactBriefEntity>> {
        val testList = List(100) {
            ContactBriefEntity(
                lookupKey = "key$it",
                displayName = "Name$it Surname$it",
                thumbnailUri = null,
                isStarred = false
            )
        }
        return flow {
            emit(testList)
        }
    }

}