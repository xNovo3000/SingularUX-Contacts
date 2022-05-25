package org.singularux.contacts.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.singularux.contacts.ui.route.ContactListRoute
import org.singularux.contacts.ui.route.SearchRoute
import org.singularux.contacts.ui.theme.ContactsTheme

// All the routes in the application
sealed class ContactsRoute(val name: String) {
    object ContactList : ContactsRoute(name = "contact_list")
    object Search : ContactsRoute(name = "search")
}

@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun ContactsUI() {
    // Wrap everything inside the theme
    ContactsTheme {
        // Initialize navigation between routes
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController = navController,
            startDestination = ContactsRoute.ContactList.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 500),
                    targetOffset = { it / 4 }
                )
            },
            // FIXME: Pop-enter is shown always above pop-exit. Should be the inverse
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 500),
                    initialOffset = { it / 4 }
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 500)
                )
            },
        ) {
            composable(ContactsRoute.ContactList.name) { ContactListRoute(navController = navController) }
            composable(ContactsRoute.Search.name) { SearchRoute(navController = navController) }
        }
    }
}