package org.singularux.contacts.feature.contactdetail.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import org.singularux.contacts.core.ui.ContactsTheme
import org.singularux.contacts.feature.contactdetail.presentation.ContactDetailViewModel
import org.singularux.contacts.feature.contactdetail.presentation.ContactDetailViewModelFactory

@AndroidEntryPoint
class ContactDetailActivity : ComponentActivity() {

    private val viewModel: ContactDetailViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<ContactDetailViewModelFactory> { factory ->
                    factory.create(lookupKey = intent.data?.lastPathSegment!!)
                }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Create activity with edge-to-edge enabled
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // Switch to compose UI
        setContent {
            ContactsTheme {
                ContactDetailUI(viewModel = viewModel)
            }
        }
    }

}