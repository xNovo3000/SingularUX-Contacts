package org.singularux.contacts.feature.contactlist.ui

import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import javax.inject.Inject

class ContactListInsetListener @Inject constructor() : OnApplyWindowInsetsListener {

    companion object {
        private const val TOP_MARGIN_DP = 56 + 8 + 8
        private const val BOTTOM_MARGIN_DP = 56 + 16 + 16
    }

    override fun onApplyWindowInsets(
        view: View,
        windowInsets: WindowInsetsCompat
    ): WindowInsetsCompat {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val density = view.context.resources.displayMetrics.density
        view.updatePadding(
            left = insets.left,
            right = insets.right,
            top = (insets.top + TOP_MARGIN_DP * density).toInt(),
            bottom = (insets.bottom + BOTTOM_MARGIN_DP * density).toInt()
        )
        return WindowInsetsCompat.CONSUMED
    }

}