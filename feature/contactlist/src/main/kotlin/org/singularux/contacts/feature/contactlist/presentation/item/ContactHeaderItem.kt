package org.singularux.contacts.feature.contactlist.presentation.item

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.singularux.contacts.core.ui.ContactsTheme
import org.singularux.contacts.feature.contactlist.R

object ContactHeaderItemType

sealed class ContactHeaderItemData {
    data object UserProfile : ContactHeaderItemData()
    data object Starred : ContactHeaderItemData()
    data object Symbols : ContactHeaderItemData()
    data class Initial(val letter: Char) : ContactHeaderItemData()
}

@Composable
fun ContactHeaderItem(
    modifier: Modifier = Modifier,
    data: ContactHeaderItemData
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = when (data) {
            is ContactHeaderItemData.Initial -> data.letter.toString()
            ContactHeaderItemData.Starred -> stringResource(R.string.contact_header_starred)
            ContactHeaderItemData.Symbols -> stringResource(R.string.contact_header_symbols)
            ContactHeaderItemData.UserProfile -> stringResource(R.string.contact_header_user_profile)
        },
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewUserProfile() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactHeaderItem(data = ContactHeaderItemData.UserProfile)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewStarred() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactHeaderItem(data = ContactHeaderItemData.Starred)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewSymbols() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactHeaderItem(data = ContactHeaderItemData.Symbols)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewInitial() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            ContactHeaderItem(data = ContactHeaderItemData.Initial(letter = 'C'))
        }
    }
}