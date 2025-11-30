package org.singularux.contacts.data.contacts.entity

sealed class PhoneNumberType {
    data object Home : PhoneNumberType()
    data object Mobile : PhoneNumberType()
    data object Work : PhoneNumberType()
    data object Other : PhoneNumberType()
    data class Custom(val label: String) : PhoneNumberType()
}