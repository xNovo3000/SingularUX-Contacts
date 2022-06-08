package org.singularux.contacts.ui.component

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.singularux.contacts.R
import org.singularux.contacts.ui.theme.ContactsTheme

@Composable
fun ContactListAppBarSelection(
    selectedContacts: Int,
    onCloseClick: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSelectAllClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    // Set color of status bar
    val colorScheme = MaterialTheme.colorScheme
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = colorScheme.surface.luminance() > 0.5F
        )
    }
    // Top app bar insets hack. Found on Google's Jetchat app example
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(
        scrollFraction = scrollBehavior.scrollFraction
    ).value
    val foregroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent
    )
    // When back is being clicked, delete the selection
    BackHandler(onBack = onCloseClick)
    // Top app bar insets hack composable implementation
    Column(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
    ) {
        // Status bar spacer above app bar
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        )
        // The app bar
        SmallTopAppBar(
            navigationIcon = {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.contact_list_x_selected,
                        selectedContacts
                    )
                )
            },
            actions = {
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Rounded.Share,
                        contentDescription = null
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null
                    )
                }
            },
            colors = foregroundColors,
            scrollBehavior = scrollBehavior
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            ContactListAppBarSelection(
                selectedContacts = 0,
                onCloseClick = {},
                onShareClick = {},
                onDeleteClick = {},
                onSelectAllClick = {},
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
                    state = rememberTopAppBarScrollState()
                )
            )
        }
    }
}