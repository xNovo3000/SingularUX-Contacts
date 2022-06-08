package org.singularux.contacts.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.singularux.contacts.R
import org.singularux.contacts.ui.theme.ContactsTheme

@Composable
fun ContactListContentEmpty() {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(
                top = 64.dp,
                bottom = 88.dp,
                start = 24.dp,
                end = 24.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(96.dp),
            imageVector = Icons.Rounded.Person,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.contact_list_empty),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            ContactListContentEmpty()
        }
    }
}