package org.singularux.contacts.feature.contactlist.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.singularux.contacts.feature.contactlist.presentation.element.NewContactFab
import org.singularux.contacts.feature.contactlist.presentation.element.SearchBarInput

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
            AppBarWithSearch(
                inputField = inputField,
                state = searchBarState,
                scrollBehavior = scrollBehavior
            )
            ExpandedFullScreenSearchBar(
                inputField = inputField,
                state = searchBarState
            ) {

            }
        },
        floatingActionButton = {
            NewContactFab(onClick = onAddContactClick)
        }
    ) { contentPadding ->
        contentPadding
        val contactListPermissionsState = rememberMultiplePermissionsState(viewModel.contactListPermissions)
        if (contactListPermissionsState.allPermissionsGranted) {
            
        } else {
            LaunchedEffect(Unit) { contactListPermissionsState.launchMultiplePermissionRequest() }
        }
    }
}