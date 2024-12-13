package org.singularux.contacts.feature.contactlist.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.singularux.contacts.feature.contactlist.R
import org.singularux.contacts.feature.contactlist.model.ContactBriefItem
import javax.inject.Inject

class ContactListAdapter @Inject constructor() :
    ListAdapter<ContactBriefItem, ContactBriefItemViewHolder>(ContactBriefItem.Callback) {

    companion object {
        private const val TAG = "ContactListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactBriefItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0, 1 -> ContactBriefItemHeaderViewHolder(
                view = layoutInflater.inflate(R.layout.contact_list_item, parent, false)
            )
            2 -> ContactBriefItemContactViewHolder(
                view = layoutInflater.inflate(R.layout.contact_list_item_header, parent, false)
            )
            else -> {
                throw RuntimeException("Invalid viewType: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ContactBriefItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = currentList[position]) {
            is ContactBriefItem.StandardHeader -> 0
            is ContactBriefItem.CustomHeader -> 1
            is ContactBriefItem.Contact -> 2
            else -> {  // Should never happen
                Log.e(TAG, "getItemViewType(): found invalid class ${item.javaClass}")
                -1
            }
        }
    }

}