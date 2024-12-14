package org.singularux.contacts.feature.contactlist.ui.viewholder

import android.view.View
import com.google.android.material.textview.MaterialTextView
import org.singularux.contacts.feature.contactlist.databinding.MissingCurrentProfileItemBinding

class MissingCurrentProfileViewHolder(
    view: View,
    binding: MissingCurrentProfileItemBinding = MissingCurrentProfileItemBinding.bind(view),
    val headline: MaterialTextView = binding.headline
) : ContactBriefItemViewHolder(view)