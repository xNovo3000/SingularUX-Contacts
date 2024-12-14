package org.singularux.contacts.feature.contactlist.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil3.ImageLoader
import coil3.load
import org.singularux.contacts.feature.contactlist.R
import org.singularux.contacts.feature.contactlist.model.ContactBriefItem
import org.singularux.contacts.feature.contactlist.ui.viewholder.ContactBriefItemViewHolder
import org.singularux.contacts.feature.contactlist.ui.viewholder.ContactViewHolder
import org.singularux.contacts.feature.contactlist.ui.viewholder.HeaderViewHolder
import org.singularux.contacts.feature.contactlist.ui.viewholder.MissingCurrentProfileViewHolder
import javax.inject.Inject

class ContactListAdapter @Inject constructor(
    private val imageLoader: ImageLoader
) : ListAdapter<ContactBriefItem, ContactBriefItemViewHolder>(ContactBriefItem.Callback) {

    companion object {
        private const val TAG = "ContactListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactBriefItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0, 1 -> HeaderViewHolder(
                view = layoutInflater.inflate(R.layout.contact_item_header, parent, false)
            )
            2 -> ContactViewHolder(
                view = layoutInflater.inflate(R.layout.contact_item, parent, false)
            )
            3 -> MissingCurrentProfileViewHolder(
                view = layoutInflater.inflate(R.layout.missing_current_profile_item, parent, false)
            )
            else -> {
                throw RuntimeException("Invalid viewType: $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ContactBriefItemViewHolder, position: Int) {
        val item = currentList[position]
        when (val viewType = getItemViewType(position)) {
            0 -> {
                holder as HeaderViewHolder
                item as ContactBriefItem.StandardHeader
                holder.root.text = holder.root.resources.getString(item.type.stringId)
            }
            1 -> {
                holder as HeaderViewHolder
                item as ContactBriefItem.CustomHeader
                holder.root.text = item.string
            }
            2 -> {
                holder as ContactViewHolder
                item as ContactBriefItem.Contact
                holder.headline.text = item.displayName
                holder.avatarText.text = item.displayName.first().uppercase()
                if (item.thumbnailUri != null) {
                    val density = holder.itemView.resources.displayMetrics.density
                    holder.avatarImage.load(
                        data = item.thumbnailUri,
                        imageLoader = imageLoader
                    ) {
                        size((40 * density).toInt())
                    }
                }
                // TODO: Go to contact page on click
            }
            3 -> {
                holder as MissingCurrentProfileViewHolder
                item as ContactBriefItem.MissingCurrentProfile
                // TODO: Add profile on click
            }
            else -> {
                Log.e(TAG, "onBindViewHolder(): found invalid viewType $viewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = currentList[position]) {
            is ContactBriefItem.StandardHeader -> 0
            is ContactBriefItem.CustomHeader -> 1
            is ContactBriefItem.Contact -> 2
            is ContactBriefItem.MissingCurrentProfile -> 3
            else -> {  // Should never happen
                Log.e(TAG, "getItemViewType(): found invalid class ${item.javaClass}")
                -1
            }
        }
    }

}