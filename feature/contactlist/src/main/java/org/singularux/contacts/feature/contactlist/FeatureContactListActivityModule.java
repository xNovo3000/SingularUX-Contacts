package org.singularux.contacts.feature.contactlist;

import android.app.Activity;

import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.RecyclerView;

import org.singularux.contacts.feature.contactlist.ui.util.OnlyContactSelectionPredicate;
import org.singularux.contacts.feature.contactlist.ui.util.StandardRecyclerViewItemDetailsLookup;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;
import lombok.val;

@Module
@InstallIn(ActivityComponent.class)
public class FeatureContactListActivityModule {

    @Provides
    @ActivityScoped
    public SelectionTracker<Long> providesLookupKeySelectionTracker(Activity activity) {
        val standardRecyclerView = (RecyclerView) activity
                .findViewById(R.id.contact_list_recyclerview);
        return new SelectionTracker.Builder<>("selection", standardRecyclerView,
                new StableIdKeyProvider(standardRecyclerView),
                new StandardRecyclerViewItemDetailsLookup(standardRecyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(new OnlyContactSelectionPredicate())
                .build();
    }

}
