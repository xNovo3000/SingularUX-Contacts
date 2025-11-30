package org.singularux.contacts.data.contacts.entity

import android.net.Uri

data class ContactDetailEntity(
    val lookupKey: String,
    val displayName: String,
    val photoUri: Uri?,
    val isStarred: Boolean,
    val isUserProfile: Boolean,
    val phoneDataList: List<PhoneDataEntity>,
    val emailDataList: List<EmailDataEntity>
)