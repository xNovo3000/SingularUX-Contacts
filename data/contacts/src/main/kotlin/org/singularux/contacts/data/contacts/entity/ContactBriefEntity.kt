package org.singularux.contacts.data.contacts.entity

data class ContactBriefEntity(
    val lookupKey: String,
    val displayName: String,
    val thumbnailUri: String?,
    val isStarred: Boolean
)