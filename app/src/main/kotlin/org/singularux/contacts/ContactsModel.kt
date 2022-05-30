package org.singularux.contacts

import androidx.annotation.StringRes

object ContactsModel {

    sealed class HeaderItem(@StringRes val id: Int) {
        object UserProfile : HeaderItem(id = R.string.model_header_user_profile)
        object Starred : HeaderItem(id = R.string.model_header_starred)
        data class Letter(val letter: Char) : HeaderItem(id = R.string.placeholder)
    }

    data class ContactItem(
        val lookupKey: String,
        val displayName: String,
        val thumbnailUri: String?,
        val isStarred: Boolean,
        val isUserProfile: Boolean,
    )

}