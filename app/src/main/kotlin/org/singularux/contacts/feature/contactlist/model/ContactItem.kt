package org.singularux.contacts.feature.contactlist.model

data class ContactItem(
    val lookupKey: String,
    val displayName: String,
    val thumbnailUri: String?
)