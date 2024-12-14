package org.singularux.contacts.feature.contactlist.ui.viewholder

import android.view.View
import com.google.android.material.textview.MaterialTextView
import org.singularux.contacts.feature.contactlist.databinding.ContactItemHeaderBinding

class HeaderViewHolder(
    view: View,
    binding: ContactItemHeaderBinding = ContactItemHeaderBinding.bind(view),
    val root: MaterialTextView = binding.root
) : ContactBriefItemViewHolder(view)