package org.singularux.contacts.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import org.singularux.contacts.model.ContactItem

@Composable
fun ContactListContent(
    selectedContacts: Set<ContactItem>,
    contacts: List<ContactItem>,
    onContactClick: (ContactItem) -> Unit,
    onContactLongClick: (ContactItem) -> Unit,
    paddingValues: PaddingValues
) {

}