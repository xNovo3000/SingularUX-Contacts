package org.singularux.contacts.feature.contactlist.ui.viewholder

import android.view.View
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import org.singularux.contacts.feature.contactlist.databinding.ContactItemBinding

class ContactViewHolder(
    view: View,
    binding: ContactItemBinding = ContactItemBinding.bind(view),
    val avatarText: MaterialTextView = binding.avatarText,
    val avatarImage: ShapeableImageView = binding.avatarImage,
    val headline: MaterialTextView = binding.headline
) : ContactBriefItemViewHolder(view)