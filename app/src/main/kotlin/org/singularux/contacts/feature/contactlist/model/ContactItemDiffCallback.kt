package org.singularux.contacts.feature.contactlist.model

import androidx.recyclerview.widget.DiffUtil

object ContactItemDiffCallback : DiffUtil.ItemCallback<ContactItem>() {

    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.lookupKey == newItem.lookupKey
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem == newItem
    }

}