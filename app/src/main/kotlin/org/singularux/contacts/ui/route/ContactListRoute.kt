package org.singularux.contacts.ui.route

import android.Manifest
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.singularux.contacts.ContactsModel
import org.singularux.contacts.ContactsViewModel
import org.singularux.contacts.ui.ContactsRoute
import org.singularux.contacts.ui.component.ContactListAppBar
import org.singularux.contacts.ui.component.ContactListContent
import org.singularux.contacts.ui.component.ContactListFloatingActionButton

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun ContactListRoute(
    navController: NavHostController,
    contactsViewModel: ContactsViewModel = viewModel()
) {
    // Permission state management
    val readContactsPermission = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)
    LaunchedEffect(readContactsPermission.status) {
        // Init ViewModel or request permission
        if (readContactsPermission.status.isGranted) {
            contactsViewModel.onGivenPermission(readContactsPermission.permission)
        } else {
            readContactsPermission.launchPermissionRequest()
        }
    }
    // Static states
    val scrollState = rememberTopAppBarScrollState()
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior(scrollState) }
    // Contacts
    var selectedContacts by remember { mutableStateOf(setOf<ContactsModel.ContactItem>()) }
    val contacts by contactsViewModel.contactList.collectAsState()
    // Update selection according to contacts
    LaunchedEffect(contacts) {
        val orphans = selectedContacts.filter { selectedContact ->
            contacts.none { selectedContact.lookupKey == it.lookupKey }
        }
        if (orphans.isNotEmpty()) {
            selectedContacts = selectedContacts - orphans.toSet()
        }
    }
    // Build
    Scaffold(
        topBar = {
            ContactListAppBar(
                selectedContacts = selectedContacts.size,
                onSearchClick = {  // Go to search page ONLY if the permission is granted
                    if (readContactsPermission.status.isGranted) {
                        navController.navigate(ContactsRoute.Search.name)
                    }
                },
                onMoreVertClick = { /*TODO*/ },
                onCloseClick = { /*TODO*/ },
                onShareClick = { /*TODO*/ },
                onDeleteClick = {  // Empty selection
                    selectedContacts = setOf()
                },
                onSelectAllClick = { /*TODO*/ },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ContactListFloatingActionButton(
                onClick = {  // Go to new contact page ONLY if the permission is granted
                    if (readContactsPermission.status.isGranted) {
                        navController.navigate(ContactsRoute.NewContact.name)
                    }
                }
            )
        }
    ) {
        ContactListContent(
            selectedContacts = selectedContacts,
            contacts = contacts,
            onContactClick = {},
            onContactLongClick = {},
            paddingValues = it
        )
    }
}