package org.singularux.contacts.ui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
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
    // Animation state management
    val currentState by remember(readContactsPermissionState.status, contacts) {
        derivedStateOf { calculateCurrentState(readContactsPermissionState.status, contacts) }
    }
    // Build
    Crossfade(targetState = currentState) {
        when (it) {
            CurrentState.MISSING_PERMISSION -> {
                ContactListContentMissingPermission(
                    readContactsPermissionState = readContactsPermissionState
                )
            }
            CurrentState.EMPTY -> {
                ContactListContentEmpty()
            }
            CurrentState.NOT_EMPTY -> {
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
}

@ExperimentalPermissionsApi
private fun calculateCurrentState(
    readContactsPermissionStatus: PermissionStatus,
    contacts: List<ContactItem>
): CurrentState {
    return when {
        !readContactsPermissionStatus.isGranted -> CurrentState.MISSING_PERMISSION
        contacts.isEmpty() -> CurrentState.EMPTY
        else -> CurrentState.NOT_EMPTY
    }
}

private enum class CurrentState {
    MISSING_PERMISSION, EMPTY, NOT_EMPTY
}