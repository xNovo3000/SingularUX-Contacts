package org.singularux.contacts.data.contacts.entity

import android.net.Uri

data class ContactBriefEntity(
    val lookupKey: String,
    val displayName: String,
    val photoThumbnailUri: Uri?,
    val isStarred: Boolean
)