package org.singularux.contacts.feature.contactlist.ui

import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.singularux.contacts.R
import org.singularux.contacts.feature.contactlist.viewmodel.ContactListViewModel

@AndroidEntryPoint
class ContactListRouteFragment : Fragment(R.layout.route_contact_list) {

    val viewModel: ContactListViewModel by hiltNavGraphViewModels(R.navigation.contacts_graph)

}