package org.singularux.contacts.feature.contactview.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import org.singularux.contacts.core.threading.BackgroundScheduler;
import org.singularux.contacts.feature.contactview.domain.GetReadContactsPermissionsUseCase;
import org.singularux.contacts.feature.contactview.domain.ListenContactUseCase;
import org.singularux.contacts.feature.contactview.ui.item.ItemContact;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.Getter;

@HiltViewModel(assistedFactory = ContactViewViewModelFactory.class)
public class ContactViewViewModel extends ViewModel {

    private final @Getter String[] readContactPermissions;

    private final @Getter LiveData<ItemContact> itemContactLiveData;

    @AssistedInject
    public ContactViewViewModel(@Assisted String lookupKey,
                                @BackgroundScheduler Scheduler backgroundScheduler,
                                GetReadContactsPermissionsUseCase getReadContactsPermissionsUseCase,
                                ListenContactUseCase listenContactUseCase) {
        this.readContactPermissions = getReadContactsPermissionsUseCase.get();
        this.itemContactLiveData = LiveDataReactiveStreams
                .fromPublisher(listenContactUseCase.get(lookupKey)
                        .observeOn(backgroundScheduler)
                        .map(new Transformations.IItemContact()));
    }

}
