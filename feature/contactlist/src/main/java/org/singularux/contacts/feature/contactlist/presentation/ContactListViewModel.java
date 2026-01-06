package org.singularux.contacts.feature.contactlist.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import org.singularux.contacts.core.threading.BackgroundScheduler;
import org.singularux.contacts.feature.contactlist.domain.GetReadContactsPermissionsUseCase;
import org.singularux.contacts.feature.contactlist.domain.ListenContactListByNameUseCase;
import org.singularux.contacts.feature.contactlist.domain.ListenContactListUseCase;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemData;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.Getter;

@HiltViewModel
public class ContactListViewModel extends ViewModel {

    private static final String TAG = "ContactListViewModel";

    private final @Getter String[] readContactsPermissions;

    private final @Getter LiveData<List<ItemData>> contactListLiveData;

    private final ListenContactListByNameUseCase.Emitter searchQueryEmitter;
    private final @Getter LiveData<List<ItemContactData>> searchContactListLiveData;

    @Inject
    public ContactListViewModel(GetReadContactsPermissionsUseCase getReadContactsPermissionsUseCase,
                                ListenContactListUseCase listenContactListUseCase,
                                ListenContactListByNameUseCase listenContactListByNameUseCase,
                                @BackgroundScheduler Scheduler backgroundScheduler) {
        // Runtime permissions
        this.readContactsPermissions = getReadContactsPermissionsUseCase.get();
        // Contact list
        this.contactListLiveData = LiveDataReactiveStreams
                .fromPublisher(listenContactListUseCase.get()
                        .observeOn(backgroundScheduler)
                        .map(new TransformationsCollection.ContactList()));
        // Search
        this.searchQueryEmitter = new ListenContactListByNameUseCase.Emitter();
        this.searchContactListLiveData = LiveDataReactiveStreams
                .fromPublisher(listenContactListByNameUseCase.get(searchQueryEmitter)
                        .observeOn(backgroundScheduler)
                        .map(new TransformationsCollection.SearchContactList()));
    }

    public void updateSearchQuery(@NonNull String query) {
        if (searchQueryEmitter.emitter != null) {
            Log.d(TAG, "Emitting new request query: " + query);
            searchQueryEmitter.emitter.onNext(query);
        }
    }

}
