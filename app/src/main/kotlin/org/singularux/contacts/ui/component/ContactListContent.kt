package org.singularux.contacts.ui.component

import androidx.compose.runtime.Composable
import org.singularux.contacts.ContactsModel

@Composable
fun ContactListContent(
    selectedContacts: Set<ContactsModel.ContactItem>,
    contacts: List<ContactsModel.ContactItem>,
    onContactClick: () -> Unit,
    onContactLongClick: () -> Unit,
) {

}