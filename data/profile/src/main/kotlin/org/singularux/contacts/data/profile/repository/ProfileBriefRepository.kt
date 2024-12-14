package org.singularux.contacts.data.profile.repository

import kotlinx.coroutines.flow.Flow
import org.singularux.contacts.data.profile.entity.ProfileBriefEntity

interface ProfileBriefRepository {
    suspend fun getProfile(): ProfileBriefEntity?
    fun listenProfile(): Flow<ProfileBriefEntity?>
}