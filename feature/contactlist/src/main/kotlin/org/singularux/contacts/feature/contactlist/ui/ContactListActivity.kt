package org.singularux.contacts.feature.contactlist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.singularux.contacts.core.ui.ContactsTheme
import org.singularux.contacts.feature.contactlist.presentation.ContactListViewModel

@AndroidEntryPoint
class ContactListActivity : ComponentActivity() {

    private val viewModel: ContactListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Start activity with splash screen and edge-to-edge
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Switch to Compose UI
        setContent {
            ContactsTheme {
                ContactListUI(
                    viewModel = viewModel,
                    onGoToContactDetailClick = { /* TODO: Launch contact detail activity */ },
                    onAddContactClick = { /* TODO: Launch new contact activity */ }
                )
            }
        }
    }

}