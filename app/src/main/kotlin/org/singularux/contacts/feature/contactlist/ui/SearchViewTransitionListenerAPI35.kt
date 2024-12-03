package org.singularux.contacts.feature.contactlist.ui

import android.util.Log
import androidx.annotation.RequiresApi
import com.google.android.material.search.SearchView

@RequiresApi(35)
class SearchViewTransitionListenerAPI35 : SearchView.TransitionListener {

    companion object {
        private const val TAG = "SearchViewTransitionListenerAPI35"
    }

    override fun onStateChanged(
        searchView: SearchView,
        previousState: SearchView.TransitionState,
        newState: SearchView.TransitionState
    ) {
        Log.d(TAG, "onStateChanged(): predictive back is enabled, do nothing")
    }
    
}