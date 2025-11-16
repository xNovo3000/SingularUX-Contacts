package org.singularux.contacts.feature.contactlist.presentation.element

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.singularux.contacts.core.ui.ContactsTheme
import org.singularux.contacts.feature.contactlist.presentation.item.ContactBriefItem
import org.singularux.contacts.feature.contactlist.presentation.item.ContactBriefItemAction
import org.singularux.contacts.feature.contactlist.presentation.item.ContactBriefItemData
import org.singularux.contacts.feature.contactlist.presentation.item.ContactBriefItemPosition
import org.singularux.contacts.feature.contactlist.presentation.item.ContactBriefItemType
import org.singularux.contacts.feature.contactlist.presentation.item.ContactHeaderItem
import org.singularux.contacts.feature.contactlist.presentation.item.ContactHeaderItemData
import org.singularux.contacts.feature.contactlist.presentation.item.ContactHeaderItemType

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    contactListData: List<Pair<ContactHeaderItemData, List<ContactBriefItemData>>>,
    onContactElementAction: (lookupKey: String, action: ContactBriefItemAction) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding
    ) {
        contactListData.forEach { (headerItemData, contactBriefItemDataList) ->
            item(
                key = headerItemData.hashCode(),
                contentType = { ContactHeaderItemType }
            ) {
                ContactHeaderItem(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 14.dp, bottom = 8.dp),
                    data = headerItemData
                )
            }
            itemsIndexed(
                items = contactBriefItemDataList,
                key = { _, item -> item.lookupKey },
                contentType = { _, _ -> ContactBriefItemType }
            ) { index, item ->
                ContactBriefItem(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 2.dp),
                    data = item,
                    position = when {
                        contactBriefItemDataList.size == 1 -> ContactBriefItemPosition.SINGLE
                        index == 0 -> ContactBriefItemPosition.START
                        index == contactBriefItemDataList.size - 1 -> ContactBriefItemPosition.END
                        else -> ContactBriefItemPosition.MIDDLE
                    },
                    onAction = { onContactElementAction(item.lookupKey, it) }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ContactsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            ContactList(
                contactListData = listOf(
                    ContactHeaderItemData.UserProfile to listOf(
                        ContactBriefItemData(
                            lookupKey = "100",
                            displayName = "User Profile",
                            photoThumbnailUri = null
                        )
                    ),
                    ContactHeaderItemData.Starred to List(5) {
                        ContactBriefItemData(
                            lookupKey = "200$it",
                            displayName = "Starred $it",
                            photoThumbnailUri = null
                        )
                    },
                    ContactHeaderItemData.Initial('A') to List(5) {
                        ContactBriefItemData(
                            lookupKey = "300$it",
                            displayName = "A $it",
                            photoThumbnailUri = null
                        )
                    },
                    ContactHeaderItemData.Initial('B') to List(5) {
                        ContactBriefItemData(
                            lookupKey = "400$it",
                            displayName = "B $it",
                            photoThumbnailUri = null
                        )
                    }
                ),
                onContactElementAction = { _, _ -> }
            )
        }
    }
}