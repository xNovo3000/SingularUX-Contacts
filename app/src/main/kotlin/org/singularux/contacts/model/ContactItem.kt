package org.singularux.contacts.model

data class ContactItem(
    val lookupKey: String,
    val displayName: String,
    val thumbnailUri: String?,
    val isStarred: Boolean,
    val isUserProfile: Boolean,
)