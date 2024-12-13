package org.singularux.contacts.feature.contactlist.model

import androidx.recyclerview.widget.DiffUtil

sealed class ContactBriefItem {

    enum class HeaderType { CURRENT_PROFILE, STARRED, NON_ALPHABET }

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

    // Maybe also profile?

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