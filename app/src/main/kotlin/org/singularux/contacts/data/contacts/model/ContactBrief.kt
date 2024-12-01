package org.singularux.contacts.data.contacts.model

data class ContactBrief(
    val lookupKey: String,
    val displayName: String,
    val thumbnailUri: String?,
    val isStarred: Boolean
)