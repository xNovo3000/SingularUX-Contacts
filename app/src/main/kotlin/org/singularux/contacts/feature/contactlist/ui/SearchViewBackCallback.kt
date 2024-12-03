package org.singularux.contacts.feature.contactlist.ui

import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.google.android.material.search.SearchView

class SearchViewBackCallback : OnBackPressedCallback(enabled = false) {

    companion object {
        private const val TAG = "SearchViewBackCallback"
    }

    var searchView: SearchView? = null

    override fun handleOnBackPressed() {
        when (val view = searchView) {
            null -> Log.e(TAG, "handleOnBackPressed(): called with a null searchView")
            else -> view.hide()
        }
    }

}