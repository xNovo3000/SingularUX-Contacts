package org.singularux.contacts.feature.contactlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil3.ImageLoader
import coil3.request.ImageRequest
import org.singularux.contacts.R
import org.singularux.contacts.feature.contactlist.model.ContactItem
import org.singularux.contacts.feature.contactlist.model.ContactItemDiffCallback

class ContactListAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<ContactItem, ContactItemViewHolder>(ContactItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        val item = currentList[position]
        holder.avatarText.text = item.displayName.first().uppercase()
        holder.bodyText.text = item.displayName
        // Load thumbnail if it is present
        if (item.thumbnailUri != null) {
            imageLoader.enqueue(
                request = ImageRequest.Builder(holder.itemView.context)
                    .data(item.thumbnailUri)
                    .build()
            )
        }
    }

}