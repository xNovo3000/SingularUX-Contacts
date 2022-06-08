package org.singularux.contacts.model

import androidx.annotation.StringRes
import org.singularux.contacts.R

sealed class HeaderItem(@StringRes val id: Int) {
    object UserProfile : HeaderItem(id = R.string.model_header_user_profile)
    object Starred : HeaderItem(id = R.string.model_header_starred)
    data class Letter(val letter: Char) : HeaderItem(id = R.string.placeholder)
}