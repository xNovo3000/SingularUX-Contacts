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
            SearchView.TransitionState.HIDING,
            SearchView.TransitionState.HIDDEN -> {
                Log.d(TAG, "onStateChanged(): disabling searchViewBackCallback")
                searchViewBackCallback.isEnabled = false
                searchViewBackCallback.searchView = null
            }
            SearchView.TransitionState.SHOWING,
            SearchView.TransitionState.SHOWN -> {
                Log.d(TAG, "onStateChanged(): enabling searchViewBackCallback")
                searchViewBackCallback.searchView = searchView
                searchViewBackCallback.isEnabled = true
            }
        }
    }

}