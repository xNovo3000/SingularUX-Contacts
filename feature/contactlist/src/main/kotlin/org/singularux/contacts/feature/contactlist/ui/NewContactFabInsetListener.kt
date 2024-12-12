package org.singularux.contacts.feature.contactlist.ui

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import javax.inject.Inject

class NewContactFabInsetListener @Inject constructor() : OnApplyWindowInsetsListener {

    companion object {
        private const val MARGIN_DP = 16
    }

    override fun onApplyWindowInsets(
        view: View,
        windowInsets: WindowInsetsCompat
    ): WindowInsetsCompat {
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
        val density = view.context.resources.displayMetrics.density
        view.updateLayoutParams<CoordinatorLayout.LayoutParams> {
            marginEnd = (insets.right + MARGIN_DP * density).toInt()
            bottomMargin = (insets.bottom + MARGIN_DP * density).toInt()
        }
        return WindowInsetsCompat.CONSUMED
    }

}