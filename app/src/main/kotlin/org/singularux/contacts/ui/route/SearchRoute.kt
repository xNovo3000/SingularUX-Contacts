package org.singularux.contacts.ui.route

import android.Manifest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.singularux.contacts.ui.component.SearchContent
import org.singularux.contacts.ui.component.SearchTopBar
import org.singularux.contacts.viewmodel.ContactListViewModel

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SearchRoute(
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
    // State
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(state = rememberTopAppBarScrollState())
    var searchValue by remember { mutableStateOf("") }
    // Build
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchTopBar(
                navController = navController,
                searchValue = searchValue,
                onSearchValueChange = { searchValue = it },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        // State
        val contacts by contactListViewModel.contactList.collectAsState()
        // Build
        SearchContent(
            contacts = contacts,
            searchValue = searchValue,
            contentPadding = it
        )
    }
}