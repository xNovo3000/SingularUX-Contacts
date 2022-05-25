package org.singularux.contacts.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.singularux.contacts.ui.theme.ContactsTheme

// All the routes in the application
sealed class ContactsRoute(val name: String) {
    object ContactList : ContactsRoute(name = "contact_list")
    object Search : ContactsRoute(name = "search")
}

@ExperimentalAnimationApi
@Composable
fun ContactsUI() {
    // Wrap everything inside the theme
    ContactsTheme {
        // Initialize navigation between routes
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(navController = navController, startDestination = ContactsRoute.ContactList.name) {
            composable(ContactsRoute.ContactList.name) {}
            composable(ContactsRoute.Search.name) {}
        }
    }
}