package org.singularux.contacts.feature.contactlist.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import org.singularux.contacts.data.contacts.repository.ContactBriefRepository
import org.singularux.contacts.data.profile.repository.ProfileBriefRepository
import org.singularux.contacts.feature.contactlist.model.ContactBrief
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(
    private val contactBriefRepository: ContactBriefRepository,
    private val profileBriefRepository: ProfileBriefRepository
) {

    companion object {
        private const val TAG = "GetAllContactsUseCase"
    }

    operator fun invoke(): Flow<List<ContactBrief>> {
        // Combine contacts and current profile
        return contactBriefRepository.listenAll()
            .combine(profileBriefRepository.listenProfile())
            { contactList, maybeProfile ->
                withContext(Dispatchers.Default) {
                    val result = mutableListOf<ContactBrief>()
                    // Personal profile always first
                    if (maybeProfile != null) {
                        result.add(
                            element = ContactBrief(
                                lookupKey = maybeProfile.lookupKey,
                                displayName = maybeProfile.displayName,
                                thumbnailUri = maybeProfile.thumbnailUri,
                                isStarred = false,
                                isCurrentProfile = true
                            )
                        )
                    }
                    // Then contacts
                    contactList.forEach {
                        result.add(
                            element = ContactBrief(
                                lookupKey = it.lookupKey,
                                displayName = it.displayName,
                                thumbnailUri = it.thumbnailUri,
                                isStarred = it.isStarred,
                                isCurrentProfile = false
                            )
                        )
                    }
                    result
                }
            }
    }

}