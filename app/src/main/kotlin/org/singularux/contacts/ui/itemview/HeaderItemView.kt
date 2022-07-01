package org.singularux.contacts.ui.itemview

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.singularux.contacts.model.HeaderItem
import org.singularux.contacts.ui.theme.ContactsTheme

@Composable
fun HeaderItemView(headerItem: HeaderItem) {
    Text(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 8.dp
            )
            .fillMaxWidth(),
        text = when (headerItem) {
            is HeaderItem.Letter -> headerItem.letter.toString()
            else -> stringResource(id = headerItem.id)
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            HeaderItemView(
                headerItem = HeaderItem.Starred
            )
        }
    }
}
