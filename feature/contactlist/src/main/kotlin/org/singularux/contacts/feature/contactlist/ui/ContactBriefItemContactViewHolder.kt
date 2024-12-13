package org.singularux.contacts.feature.contactlist.ui

import android.view.View
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import org.singularux.contacts.feature.contactlist.databinding.ContactListItemBinding

class ContactBriefItemContactViewHolder(
    view: View,
    binding: ContactListItemBinding = ContactListItemBinding.bind(view),
    val avatarText: MaterialTextView = binding.avatarText,
    val avatarImage: ShapeableImageView = binding.avatarImage,
    val headline: MaterialTextView = binding.headline
) : ContactBriefItemViewHolder(view)