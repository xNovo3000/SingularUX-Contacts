package org.singularux.contacts.feature.contactlist.ui.element

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.singularux.contacts.core.ui.ContactsTheme
import org.singularux.contacts.feature.contactlist.R

@Composable
fun PaddingValues.withFabPadding(): PaddingValues = PaddingValues(
    top = this.calculateTopPadding(),
    bottom = this.calculateBottomPadding() + 112.dp,
    start = this.calculateStartPadding(LocalLayoutDirection.current),
    end = this.calculateEndPadding(LocalLayoutDirection.current)
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NewContactFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MediumFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            modifier = Modifier.size(FloatingActionButtonDefaults.MediumIconSize),
            painter = painterResource(R.drawable.add_28dp),
            contentDescription = stringResource(R.string.new_contact_fab_description)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ContactsTheme {
        Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
            NewContactFab {}
        }
    }
}