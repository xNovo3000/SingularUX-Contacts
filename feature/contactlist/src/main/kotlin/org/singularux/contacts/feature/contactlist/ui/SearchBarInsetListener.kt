package org.singularux.contacts.feature.contactlist.ui

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import javax.inject.Inject

class SearchBarInsetListener @Inject constructor() : OnApplyWindowInsetsListener {

    companion object {
        private const val HORIZONTAL_MARGIN_DP = 12
        private const val TOP_MARGIN_DP = 8
    }

    override fun onApplyWindowInsets(
        view: View,
        windowInsets: WindowInsetsCompat
    ): WindowInsetsCompat {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
        val density = view.context.resources.displayMetrics.density
        view.updateLayoutParams<CoordinatorLayout.LayoutParams> {
            marginStart = (insets.left + HORIZONTAL_MARGIN_DP * density).toInt()
            marginEnd = (insets.right + HORIZONTAL_MARGIN_DP * density).toInt()
            topMargin = (insets.top + TOP_MARGIN_DP * density).toInt()
        }
        return WindowInsetsCompat.CONSUMED
    }

}