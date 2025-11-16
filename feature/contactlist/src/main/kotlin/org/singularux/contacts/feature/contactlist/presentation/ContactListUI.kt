package org.singularux.contacts.feature.contactlist.presentation

import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import org.singularux.contacts.feature.contactlist.presentation.element.NewContactFab
import org.singularux.contacts.feature.contactlist.presentation.element.SearchBarInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListUI(
    viewModel: ContactListViewModel,
    onGoToContactDetailClick: (lookupKey: String) -> Unit,
    onAddContactClick: () -> Unit
) {
    Scaffold(
        topBar = {
            val searchBarState = rememberSearchBarState()
            val inputField = @Composable {
                SearchBarInput(
                    textFieldState = viewModel.searchBarTextFieldState,
                    searchBarState = searchBarState
                )
            }
            SearchBar(
                inputField = inputField,
                state = searchBarState
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
    }
}