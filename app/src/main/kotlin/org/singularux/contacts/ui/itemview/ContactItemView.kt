package org.singularux.contacts.ui.itemview

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.singularux.contacts.ContactsModel
import org.singularux.contacts.ui.theme.ContactsTheme

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun ContactItemView(
    contactItem: ContactsModel.ContactItem,
    onClick: (ContactsModel.ContactItem) -> Unit,
    onLongClick: (ContactsModel.ContactItem) -> Unit,
    isSelected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(
                start = 8.dp,
                top = 2.dp,
                bottom = 2.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    bottomStart = 28.dp
                )
            )
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.surfaceVariant
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .combinedClickable(
                onClick = { onClick(contactItem) },
                onLongClick = { onLongClick(contactItem) }
            )
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(
                start = 8.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contactItem.displayName[0].uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                AsyncImage(
                    modifier = Modifier.size(40.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .size(
                            size = with(LocalDensity.current) {
                                40.dp.toPx().toInt()
                            }
                        )
                        .data(contactItem.thumbnailUri)
                        .crossfade(250)
                        .build(),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1F),
            text = contactItem.displayName,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            ContactItemView(
                contactItem = ContactsModel.ContactItem(
                    lookupKey = "key",
                    displayName = "Name Surname",
                    thumbnailUri = "content://contacts/key",
                    isStarred = false,
                    isUserProfile = false
                ),
                isSelected = false,
                onClick = {},
                onLongClick = {}
            )
        }
    }
}
