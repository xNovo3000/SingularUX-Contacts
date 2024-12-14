package org.singularux.contacts.feature.contactlist.ui

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.singularux.contacts.core.permission.ContactsPermission
import org.singularux.contacts.core.permission.PermissionManager
import org.singularux.contacts.feature.contactlist.R
import org.singularux.contacts.feature.contactlist.databinding.RouteContactListBinding
import org.singularux.contacts.feature.contactlist.viewmodel.ContactListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ContactListRoute : Fragment(R.layout.route_contact_list) {

    private val viewModel: ContactListViewModel by viewModels()
    private lateinit var binding: RouteContactListBinding

    private lateinit var permissionRequest: ActivityResultLauncher<String>
    @Inject lateinit var permissionManager: PermissionManager
    @Inject lateinit var contactListInsetListener: ContactListInsetListener
    @Inject lateinit var newContactFabInsetListener: NewContactFabInsetListener
    @Inject lateinit var searchBarInsetListener: SearchBarInsetListener
    @Inject lateinit var contactListAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create request permissions and actions
        permissionRequest = registerForActivityResult(RequestPermission()) { granted ->
            if (granted) {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        // TODO: Collect contacts and send to adapters
                    }
                }
            } else {
                // TODO: Show snackbar asking user to accept the request
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RouteContactListBinding.bind(view)
        // Add inset listeners
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactList, contactListInsetListener)
        ViewCompat.setOnApplyWindowInsetsListener(binding.newContactFab, newContactFabInsetListener)
        ViewCompat.setOnApplyWindowInsetsListener(binding.searchBar, searchBarInsetListener)
        // Set list adapters
        binding.contactList.adapter = contactListAdapter
        // Request contacts read permission
        permissionRequest.launch(permissionManager.getPermissionString(ContactsPermission.READ_CONTACTS))
    }

}