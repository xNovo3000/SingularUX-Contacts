package org.singularux.contacts.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import org.singularux.contacts.model.ContactItem

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@Composable
fun ContactListContent(
    readContactsPermissionState: PermissionState,
    selectedContacts: Set<String>,
    contacts: List<ContactItem>,
    onContactClick: (ContactItem) -> Unit,
    onContactLongClick: (ContactItem) -> Unit,
    paddingValues: PaddingValues
) {
    // TODO: Add cross-fade animation
    when {
        !readContactsPermissionState.status.isGranted -> {
            ContactListContentMissingPermission(
                readContactsPermissionState = readContactsPermissionState
            )
        }
        contacts.isEmpty() -> {
            ContactListContentEmpty()
        }
        else -> {
            ContactListContentNotEmpty(
                selectedContacts = selectedContacts,
                contacts = contacts,
                onContactClick = onContactClick,
                onContactLongClick = onContactLongClick,
                paddingValues = paddingValues
            )
        }
    }
}