package org.singularux.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class ContactsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Start activity with splash screen and edge-to-edge support
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }

}