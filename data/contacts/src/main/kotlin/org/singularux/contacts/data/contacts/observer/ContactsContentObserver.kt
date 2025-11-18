package org.singularux.contacts.data.contacts.observer

interface ContactsContentObserver {
    suspend fun onChange()
}