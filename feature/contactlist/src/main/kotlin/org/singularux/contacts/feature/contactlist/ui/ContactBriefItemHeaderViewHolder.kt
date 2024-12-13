package org.singularux.contacts.feature.contactlist.ui

import android.view.View
import com.google.android.material.textview.MaterialTextView
import org.singularux.contacts.feature.contactlist.databinding.ContactListItemHeaderBinding

class ContactBriefItemHeaderViewHolder(
    view: View,
    binding: ContactListItemHeaderBinding = ContactListItemHeaderBinding.bind(view),
    val root: MaterialTextView = binding.root
) : ContactBriefItemViewHolder(view)