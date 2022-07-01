package org.singularux.contacts.ui.route

import android.Manifest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.singularux.contacts.viewmodel.ContactListViewModel
import org.singularux.contacts.ui.ContactsRoute
import org.singularux.contacts.ui.component.ContactListAppBar
import org.singularux.contacts.ui.component.ContactListContent
import org.singularux.contacts.ui.component.ContactListFloatingActionButton

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun ContactListRoute(
    navController: NavHostController,
    contactListViewModel: ContactListViewModel = viewModel()
) {
    // Permission state management
    val readContactsPermission = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS)
    LaunchedEffect(readContactsPermission.status) {
        // Init ViewModel or request permission
        if (readContactsPermission.status.isGranted) {
            contactListViewModel.onGivenPermission(readContactsPermission.permission)
        } else {
            readContactsPermission.launchPermissionRequest()
        }
    }
    // Static states
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(state = rememberTopAppBarScrollState())
    val contentListState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { contentListState.firstVisibleItemIndex } }
    // Contacts
    var selectedContacts by remember { mutableStateOf(setOf<String>()) }
    val contacts by contactListViewModel.contactList.collectAsState()
    // Update selection according to contacts
    LaunchedEffect(contacts) {
        val orphans = selectedContacts.filter { selectedContact ->
            contacts.none { selectedContact == it.lookupKey }
        }
        if (orphans.isNotEmpty()) {
            selectedContacts = selectedContacts - orphans.toSet()
        }
    }
    // Build
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ContactListAppBar(
                selectedContacts = selectedContacts.size,
                onSearchClick = {  // Go to search page ONLY if the permission is granted
                    if (readContactsPermission.status.isGranted) {
                        navController.navigate(ContactsRoute.Search.name)
                    }
                },
                onMoreVertClick = { /*TODO*/ },
                onCloseClick = { selectedContacts = setOf() },
                onShareClick = { /*TODO*/ },
                onDeleteClick = { /*TODO*/ },
                onSelectAllClick = { selectedContacts = contacts.map { it.lookupKey }.toSet() },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ContactListFloatingActionButton(
                expanded = remember { derivedStateOf { contentListState.firstVisibleItemIndex } } == 0
            ) {  // Go to new contact page ONLY if the permission is granted
                if (readContactsPermission.status.isGranted) {
                    navController.navigate(ContactsRoute.NewContact.name)
                }
            }
        }
    ) { paddingValues ->
        ContactListContent(
            readContactsPermissionState = readContactsPermission,
            selectedContacts = selectedContacts,
            contacts = contacts,
            onContactClick = {
                if (selectedContacts.isEmpty()) {
                    navController.navigate("${ContactsRoute.NewContact.name}/${it.lookupKey}")
                } else if (selectedContacts.contains(it.lookupKey)) {
                    selectedContacts = selectedContacts - it.lookupKey
                } else {
                    selectedContacts = selectedContacts + it.lookupKey
                }
            },
            onContactLongClick = {
                selectedContacts = if (selectedContacts.contains(it.lookupKey)) {
                    selectedContacts - it.lookupKey
                } else {
                    selectedContacts + it.lookupKey
                }
            },
            paddingValues = paddingValues
        )
    }
}