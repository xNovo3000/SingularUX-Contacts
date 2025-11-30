package org.singularux.contacts.data.contacts.repository

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity
import org.singularux.contacts.data.contacts.entity.ContactDetailEntity

interface ContactsRepository {
    suspend fun getAll(): List<ContactBriefEntity>
    suspend fun getByDisplayNameLike(query: String, limit: Int): List<ContactBriefEntity>
    suspend fun getByLookupKey(lookupKey: String): ContactDetailEntity?
}