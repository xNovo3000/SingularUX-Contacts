package org.singularux.contacts.feature.contactlist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.search.SearchView
import dagger.hilt.android.AndroidEntryPoint
import org.singularux.contacts.R
import org.singularux.contacts.databinding.RouteContactListBinding
import org.singularux.contacts.feature.contactlist.viewmodel.ContactListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ContactListRouteFragment : Fragment(R.layout.route_contact_list) {

    private val viewModel: ContactListViewModel by hiltNavGraphViewModels(R.navigation.contacts_graph)
    private lateinit var binding: RouteContactListBinding

    @Inject lateinit var searchViewTransitionListener: SearchView.TransitionListener
    @Inject lateinit var searchViewBackCallback: SearchViewBackCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RouteContactListBinding.bind(view)
        binding.searchView.addTransitionListener(searchViewTransitionListener)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, searchViewBackCallback)
    }

}