package org.singularux.contacts.feature.contactlist.presentation

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.singularux.contacts.core.ui.ContactsTheme

data class ContactBriefItemData(
    val lookupKey: String,
    val displayName: String,
    val photoThumbnailUri: Uri?
)

enum class ContactBriefItemPosition(val shape: Shape) {
    START(shape = RoundedCornerShape(
        topStart = 16.dp, topEnd = 16.dp,
        bottomStart = 4.dp, bottomEnd = 4.dp
    )),
    MIDDLE(shape = RoundedCornerShape(4.dp)),
    END(shape = RoundedCornerShape(
        topStart = 4.dp, topEnd = 4.dp,
        bottomStart = 16.dp, bottomEnd = 16.dp
    )),
    SINGLE(shape = RoundedCornerShape(16.dp))
}

enum class ContactBriefItemAction {
    GO_TO_DETAIL, SELECT
}

@Composable
fun ContactBriefItem(
    modifier: Modifier = Modifier,
    data: ContactBriefItemData,
    position: ContactBriefItemPosition,
    onAction: (ContactBriefItemAction) -> Unit
) {
    ListItem(
        modifier = modifier
            .clip(shape = position.shape)
            .combinedClickable(
                onClick = { onAction(ContactBriefItemAction.GO_TO_DETAIL) },
                onLongClick = { onAction(ContactBriefItemAction.SELECT) }
            ),
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = data.displayName.firstOrNull()?.toString() ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                AsyncImage(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50)),
                    model = data.photoThumbnailUri,
                    contentDescription = data.displayName
                )
            }
        },
        headlineContent = {
            Text(
                text = data.displayName,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewStart() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactBriefItem(
                data = ContactBriefItemData(
                    lookupKey = "",
                    displayName = "Name Surname",
                    photoThumbnailUri = null
                ),
                position = ContactBriefItemPosition.START
            ) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewMiddle() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactBriefItem(
                data = ContactBriefItemData(
                    lookupKey = "",
                    displayName = "Name Surname",
                    photoThumbnailUri = null
                ),
                position = ContactBriefItemPosition.MIDDLE
            ) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewEnd() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactBriefItem(
                data = ContactBriefItemData(
                    lookupKey = "",
                    displayName = "Name Surname",
                    photoThumbnailUri = null
                ),
                position = ContactBriefItemPosition.END
            ) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSingle() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactBriefItem(
                data = ContactBriefItemData(
                    lookupKey = "",
                    displayName = "Name Surname",
                    photoThumbnailUri = null
                ),
                position = ContactBriefItemPosition.SINGLE
            ) {}
        }
    }
}