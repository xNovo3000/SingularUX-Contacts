package org.singularux.contacts.feature.contactlist

import android.os.Build
import com.google.android.material.search.SearchView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import org.singularux.contacts.feature.contactlist.ui.SearchViewBackCallback
import org.singularux.contacts.feature.contactlist.ui.SearchViewTransitionListener
import org.singularux.contacts.feature.contactlist.ui.SearchViewTransitionListenerAPI35

@Module
@InstallIn(FragmentComponent::class)
object FeatureContactListModule {

    @Provides
    @FragmentScoped
    fun providesSearchViewBackCallback(): SearchViewBackCallback {
        return SearchViewBackCallback()
    }

    @Provides
    @FragmentScoped
    fun providesSearchViewTransitionListener(
        callback: SearchViewBackCallback
    ): SearchView.TransitionListener {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            SearchViewTransitionListenerAPI35()
        } else {
            SearchViewTransitionListener(searchViewBackCallback = callback)
        }
    }

}