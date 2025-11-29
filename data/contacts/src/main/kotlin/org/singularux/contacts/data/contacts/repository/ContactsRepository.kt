package org.singularux.contacts.data.contacts.repository

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity

interface ContactsRepository {
    suspend fun getAll(): List<ContactBriefEntity>
    suspend fun getByDisplayNameLike(query: String, limit: Int): List<ContactBriefEntity>
}