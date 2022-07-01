package org.singularux.contacts.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.singularux.contacts.R
import org.singularux.contacts.ui.theme.ContactsTheme

@Composable
fun ContactListFloatingActionButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.navigationBarsPadding(),
        text = {
            Text(text = stringResource(id = R.string.contact_list_new_contact))
        },
        expanded = expanded,
        icon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null
            )
        },
        onClick = onClick
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            ContactListFloatingActionButton(true) {}
        }
    }
}