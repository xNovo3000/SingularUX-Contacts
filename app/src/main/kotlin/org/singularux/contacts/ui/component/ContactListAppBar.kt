package org.singularux.contacts.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun ContactListAppBar(
    selectedContacts: Int,
    onSearchClick: () -> Unit,
    onMoreVertClick: () -> Unit,
    onCloseClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    if (selectedContacts == 0) {
        ContactListAppBarDefault(
            onSearchClick = onSearchClick,
            onMoreVertClick = onMoreVertClick
        )
    } else {
        ContactListAppBarSelection(
            selectedContacts = selectedContacts,
            onCloseClick = onCloseClick,
            onShareClick = onShareClick,
            onDeleteClick = onDeleteClick,
            onSelectAllClick = onSelectAllClick,
            scrollBehavior = scrollBehavior
        )
    }
}