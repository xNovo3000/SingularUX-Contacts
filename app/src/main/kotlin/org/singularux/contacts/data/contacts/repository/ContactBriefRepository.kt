package org.singularux.contacts.data.contacts.repository

import kotlinx.coroutines.flow.Flow
import org.singularux.contacts.data.contacts.model.ContactBrief

interface ContactBriefRepository {
    suspend fun getAll(): List<ContactBrief>
    fun listenAll(): Flow<List<ContactBrief>>
}