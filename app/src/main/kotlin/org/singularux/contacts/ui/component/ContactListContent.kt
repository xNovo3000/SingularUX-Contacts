package org.singularux.contacts.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import org.singularux.contacts.model.ContactItem

@ExperimentalPermissionsApi
@Composable
fun ContactListContent(
    readContactsPermissionState: PermissionState,
    selectedContacts: Set<ContactItem>,
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

        }
        else -> {

        }
    }
}