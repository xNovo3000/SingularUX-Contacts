package org.singularux.contacts

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class ContactsRoute : NavKey {
    @Serializable data object ContactList : ContactsRoute()
}

@Composable
fun ContactsUI() {

}