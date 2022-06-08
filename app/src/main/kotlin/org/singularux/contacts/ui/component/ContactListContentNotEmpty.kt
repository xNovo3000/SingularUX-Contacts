package org.singularux.contacts.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import org.singularux.contacts.model.ContactItem
import org.singularux.contacts.model.HeaderItem
import org.singularux.contacts.ui.itemview.ContactItemView
import org.singularux.contacts.ui.itemview.HeaderItemView

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun ContactListContentNotEmpty(
    selectedContacts: Set<String>,
    contacts: List<ContactItem>,
    onContactClick: (ContactItem) -> Unit,
    onContactLongClick: (ContactItem) -> Unit,
    paddingValues: PaddingValues
) {
    // Get derived contacts
    val derivedContacts by remember(contacts) { derivedStateOf { deriveContacts(contacts) } }
    // Build
    LazyColumn(
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding(),
            bottom = 88.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        )
    ) {
        derivedContacts.forEach {
            // Create the header
            item(
                key = when (val key = it.key) {
                    is HeaderItem.Letter -> -key.letter.code.toLong()
                    else -> -key.id.toLong()
                }
            ) {
                HeaderItemView(headerItem = it.key)
            }
            // Create the contacts
            items(
                items = it.value,
                key = { contactItem -> contactItem.lookupKey }
            ) { item ->
                ContactItemView(
                    contactItem = item,
                    onClick = onContactClick,
                    onLongClick = onContactLongClick,
                    isSelected = selectedContacts.contains(item.lookupKey)
                )
            }
        }
    }
}

private fun deriveContacts(contacts: List<ContactItem>): Map<HeaderItem, List<ContactItem>> {
    // Generate the result
    val result = mutableMapOf<HeaderItem, MutableList<ContactItem>>()
    // Split contacts
    contacts.forEach {
        when {
            it.isUserProfile -> result.getOrPut(HeaderItem.UserProfile) { mutableListOf() }.add(it)
            it.isStarred -> result.getOrPut(HeaderItem.Starred) { mutableListOf() }.add(it)
            !it.displayName.first().isLetter() -> {
                result.getOrPut(HeaderItem.Symbols) { mutableListOf() }.add(it)
            }
            else -> {
                result.getOrPut(HeaderItem.Letter(letter = it.displayName.first().uppercaseChar())) {
                    mutableListOf()
                }.add(it)
            }
        }
    }
    // Return the result
    return result
}