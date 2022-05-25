package org.singularux.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import org.singularux.contacts.ui.theme.ContactsTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set splash screen
        installSplashScreen()
        // Create the Android view
        super.onCreate(savedInstanceState)
        // Draw behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // Set Compose content. THIS IS A PLACEHOLDER FOR THE REAL UI
        setContent { ContactsTheme {} }
    }

}