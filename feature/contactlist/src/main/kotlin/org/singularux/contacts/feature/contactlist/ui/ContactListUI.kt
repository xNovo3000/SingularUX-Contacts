package org.singularux.contacts.feature.contactlist.ui

import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.singularux.contacts.feature.contactlist.presentation.ContactListViewModel
import org.singularux.contacts.feature.contactlist.ui.element.ContactList
import org.singularux.contacts.feature.contactlist.ui.element.NewContactFab
import org.singularux.contacts.feature.contactlist.ui.element.SearchBarInput
import org.singularux.contacts.feature.contactlist.ui.element.SearchBarProtection
import org.singularux.contacts.feature.contactlist.ui.element.withFabPadding
import org.singularux.contacts.feature.contactlist.ui.item.ContactBriefItemAction

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ContactListUI(
    viewModel: ContactListViewModel,
    onGoToContactDetailClick: (lookupKey: String) -> Unit,
    onAddContactClick: () -> Unit
) {
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            val searchBarState = rememberSearchBarState()
            val inputField = @Composable {
                SearchBarInput(
                    textFieldState = viewModel.searchBarTextFieldState,
                    searchBarState = searchBarState
                )
            }
            TopSearchBar(  // Do not use AppBarWithSearch
                inputField = inputField,
                state = searchBarState,
                scrollBehavior = scrollBehavior
            )
            SearchBarProtection()
            ExpandedFullScreenSearchBar(
                inputField = inputField,
                state = searchBarState
            ) {

            }
        },
        floatingActionButton = {
            NewContactFab(onClick = onAddContactClick)
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) { contentPadding ->
        val contactListPermissionsState = rememberMultiplePermissionsState(viewModel.contactListPermissions)
        if (contactListPermissionsState.allPermissionsGranted) {
            val contactListData by viewModel.contactListData.collectAsStateWithLifecycle()
            ContactList(
                contentPadding = contentPadding.withFabPadding(),
                contactListData = contactListData,
                onContactElementAction = { lookupKey, action ->
                    when (action) {
                        ContactBriefItemAction.GO_TO_DETAIL -> onGoToContactDetailClick(lookupKey)
                        ContactBriefItemAction.SELECT -> {}
                    }
                }
            )
        } else {
            LaunchedEffect(Unit) { contactListPermissionsState.launchMultiplePermissionRequest() }
        }
    }
}