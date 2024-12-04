package org.singularux.contacts.feature.contactlist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.ImageLoader
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.singularux.contacts.R
import org.singularux.contacts.databinding.RouteContactListBinding
import org.singularux.contacts.feature.contactlist.viewmodel.ContactListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ContactListRouteFragment : Fragment(R.layout.route_contact_list) {

    private val viewModel: ContactListViewModel by hiltNavGraphViewModels(R.id.contacts_graph)
    private lateinit var binding: RouteContactListBinding

    @Inject lateinit var searchViewTransitionListener: SearchView.TransitionListener
    @Inject lateinit var searchViewBackCallback: SearchViewBackCallback
    @Inject lateinit var imageLoader: ImageLoader
    private lateinit var contactListAdapter: ContactListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RouteContactListBinding.bind(view)
        // Adjust search view
        binding.searchView.addTransitionListener(searchViewTransitionListener)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, searchViewBackCallback)
        // Initialize adapter
        contactListAdapter = ContactListAdapter(imageLoader = imageLoader)
        binding.contactList.adapter = contactListAdapter
        // Collect data
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contactListFlow.collect { contactList ->
                    contactListAdapter.submitList(contactList)
                }
            }
        }
    }

}