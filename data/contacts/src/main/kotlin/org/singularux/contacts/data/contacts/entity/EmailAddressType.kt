package org.singularux.contacts.data.contacts.entity

sealed class EmailAddressType {
    data object Home : EmailAddressType()
    data object Mobile : EmailAddressType()
    data object Work : EmailAddressType()
    data object Other : EmailAddressType()
    data class Custom(val label: String) : EmailAddressType()
}