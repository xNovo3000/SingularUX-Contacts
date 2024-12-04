package org.singularux.contacts.feature.contactlist.ui

import android.util.Log
import com.google.android.material.search.SearchView

class SearchViewTransitionListener(
    private val searchViewBackCallback: SearchViewBackCallback
) : SearchView.TransitionListener {

    companion object {
        private const val TAG = "SearchViewTransitionListener"
    }

    override fun onStateChanged(
        searchView: SearchView,
        previousState: SearchView.TransitionState,
        newState: SearchView.TransitionState
    ) {
        when (newState) {
            SearchView.TransitionState.HIDDEN -> {
                // Disable onBackPressed
                Log.d(TAG, "onStateChanged(): disabling onBackPressed for searchViewBackCallback")
                searchViewBackCallback.isEnabled = false
                searchViewBackCallback.searchView = null
            }
            SearchView.TransitionState.SHOWING -> {
                // Enable onBackPressed
                Log.d(TAG, "onStateChanged(): enabling onBackPressed for searchViewBackCallback")
                searchViewBackCallback.searchView = searchView
                searchViewBackCallback.isEnabled = true
            }
            else -> {}
        }
    }

}