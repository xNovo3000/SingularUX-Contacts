package org.singularux.contacts.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.singularux.contacts.R
import org.singularux.contacts.ui.theme.ContactsTheme

@ExperimentalMaterial3Api
@Composable
fun ContactListAppBarDefault(
    onSearchClick: () -> Unit,
    onMoreVertClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .statusBarsPadding()
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        shape = RoundedCornerShape(26.dp),
        onClick = onSearchClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 52.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.contact_list_search_contacts),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp
            )
            IconButton(onClick = onMoreVertClick) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            ContactListAppBarDefault(
                onSearchClick = {},
                onMoreVertClick = {}
            )
        }
    }
}