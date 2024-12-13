package org.singularux.contacts.feature.contactlist.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.singularux.contacts.feature.contactlist.model.ContactBrief
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor() {

    companion object {
        private const val TAG = "GetAllContactsUseCase"
    }

    operator fun invoke(): Flow<List<ContactBrief>> {
        return flow { emit(emptyList()) }
    }

}