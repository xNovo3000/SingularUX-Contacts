package org.singularux.contacts.feature.contactlist.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import org.singularux.contacts.databinding.ContactItemBinding

class ContactItemViewHolder(
    view: View,
    binding: ContactItemBinding = ContactItemBinding.bind(view),
    val avatarText: MaterialTextView = binding.avatarText,
    val avatarImage: ShapeableImageView = binding.avatarImage,
    val bodyText: MaterialTextView = binding.bodyText,
) : RecyclerView.ViewHolder(view)