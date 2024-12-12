package org.singularux.contacts.data.contacts.repository

import kotlinx.coroutines.flow.Flow
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity

interface ContactBriefRepository {
    suspend fun getAll(): List<ContactBriefEntity>
    fun listenAll(): Flow<List<ContactBriefEntity>>
}