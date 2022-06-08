package org.singularux.contacts.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import org.singularux.contacts.ui.route.ContactListRoute
import org.singularux.contacts.ui.route.SearchRoute
import org.singularux.contacts.ui.theme.*

// All the routes in the application
sealed class ContactsRoute(val name: String) {
    object ContactList : ContactsRoute(name = "contact_list")
    object Search : ContactsRoute(name = "search")
    object NewContact : ContactsRoute(name = "new_contact")
    object ContactView : ContactsRoute(name = "contact_view")
}

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun ContactsUI() {
    // Wrap everything inside the theme
    ContactsTheme {
        // Surface color when animations are running
        Surface {
            // Initialize navigation between routes
            val navController = rememberAnimatedNavController()
            AnimatedNavHost(
                navController = navController,
                startDestination = ContactsRoute.ContactList.name,
                enterTransition = EnterTransition,
                exitTransition = ExitTransition,
            ) {
                composable(ContactsRoute.ContactList.name) { ContactListRoute(navController = navController) }
                composable(ContactsRoute.Search.name) { SearchRoute(navController = navController) }
            }
        }
    }
}