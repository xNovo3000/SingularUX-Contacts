package org.singularux.contacts.feature.contactlist.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.singularux.contacts.feature.contactlist.R
import org.singularux.contacts.feature.contactlist.databinding.RouteContactListBinding
import org.singularux.contacts.feature.contactlist.viewmodel.ContactListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ContactListRoute : Fragment(R.layout.route_contact_list) {

    private val viewModel: ContactListViewModel by viewModels()
    private lateinit var binding: RouteContactListBinding

    @Inject lateinit var contactListInsetListener: ContactListInsetListener
    @Inject lateinit var newContactFabInsetListener: NewContactFabInsetListener
    @Inject lateinit var searchBarInsetListener: SearchBarInsetListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RouteContactListBinding.bind(view)
        // Add inset listeners
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactList, contactListInsetListener)
        ViewCompat.setOnApplyWindowInsetsListener(binding.newContactFab, newContactFabInsetListener)
        ViewCompat.setOnApplyWindowInsetsListener(binding.searchBar, searchBarInsetListener)
    }

}