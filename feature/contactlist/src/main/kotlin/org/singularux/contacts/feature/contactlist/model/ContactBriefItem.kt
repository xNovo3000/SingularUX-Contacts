package org.singularux.contacts.feature.contactlist.model

import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import org.singularux.contacts.feature.contactlist.R

sealed class ContactBriefItem {

    enum class HeaderType(@StringRes val stringId: Int) {
        CURRENT_PROFILE(R.string.header_current_profile),
        STARRED(R.string.header_starred),
        SYMBOLS(R.string.header_symbols)
    }

    data class StandardHeader(
        val type: HeaderType
    ) : ContactBriefItem()

    data class CustomHeader(
        val string: String
    ) : ContactBriefItem()

    data class Contact(
        val lookupKey: String,
        val displayName: String,
        val thumbnailUri: String?
    ) : ContactBriefItem()

    // Maybe also current profile?

    object Callback : DiffUtil.ItemCallback<ContactBriefItem>() {

        override fun areItemsTheSame(
            oldItem: ContactBriefItem,
            newItem: ContactBriefItem
        ): Boolean {
            return when (oldItem) {
                is StandardHeader -> when (newItem) {
                    is StandardHeader -> oldItem.type == newItem.type
                    else -> false
                }
                is CustomHeader -> when (newItem) {
                    is CustomHeader -> oldItem.string == newItem.string
                    else -> false
                }
                is Contact -> when (newItem) {
                    is Contact -> oldItem.lookupKey == newItem.lookupKey
                    else -> false
                }
            }
        }

        override fun areContentsTheSame(
            oldItem: ContactBriefItem,
            newItem: ContactBriefItem
        ): Boolean {
            return oldItem == newItem
        }

    }

}