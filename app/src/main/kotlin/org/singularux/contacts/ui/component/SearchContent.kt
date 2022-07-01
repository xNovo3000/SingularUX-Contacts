package org.singularux.contacts.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.singularux.contacts.model.ContactItem
import org.singularux.contacts.ui.itemview.ContactItemView

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun SearchContent(
    contacts: List<ContactItem>,
    searchValue: String,
    contentPadding: PaddingValues
) {
    // State
    val filteredContacts by remember(contacts, searchValue) {
        derivedStateOf {
            contacts.filter {
                it.displayName.lowercase().contains(searchValue.lowercase())
            }
        }
    }
    val lazyListState = rememberLazyListState()
    // Scroll to first item when updated
    LaunchedEffect(filteredContacts) {
        lazyListState.animateScrollToItem(0)
    }
    // Build
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(
            top = contentPadding.calculateTopPadding(),
            bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        )
    ) {
        items(
            items = filteredContacts,
            key = { it.lookupKey }
        ) {
            ContactItemView(
                contactItem = it,
                onClick = {},
                onLongClick = {},
                isSelected = false
            )
        }
    }
}