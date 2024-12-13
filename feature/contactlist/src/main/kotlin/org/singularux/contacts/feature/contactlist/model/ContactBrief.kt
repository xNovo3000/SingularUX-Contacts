package org.singularux.contacts.feature.contactlist.model

data class ContactBrief(
    val lookupKey: String,
    val displayName: String,
    val thumbnailUri: String?,
    val isStarred: Boolean,
    val isCurrentProfile: Boolean
)