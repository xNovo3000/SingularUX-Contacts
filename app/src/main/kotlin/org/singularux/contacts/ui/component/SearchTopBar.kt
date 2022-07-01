package org.singularux.contacts.ui.component

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.singularux.contacts.R
import org.singularux.contacts.ui.theme.ContactsTheme

@Composable
fun SearchTopBar(
    navController: NavHostController,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    // Set color of status bar
    val lightIcons = isSystemInDarkTheme()
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !lightIcons
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
    // Build
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
            colors = foregroundColors,
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                IconButton(
                    onClick = {  // Go back
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            title = {
                if (searchValue == "") {
                    Text(
                        text = stringResource(id = R.string.contact_list_search_dot),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchValue,
                    onValueChange = onSearchValueChange,
                    maxLines = 1,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = LocalContentColor.current
                    )
                )
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            SearchTopBar(
                navController = rememberAnimatedNavController(),
                searchValue = "",
                onSearchValueChange = {},
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
                    state = rememberTopAppBarScrollState()
                )
            )
        }
    }
}