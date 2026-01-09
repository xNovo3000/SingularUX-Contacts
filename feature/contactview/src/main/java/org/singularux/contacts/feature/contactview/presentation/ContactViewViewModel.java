package org.singularux.contacts.feature.contactview.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import org.singularux.contacts.core.threading.BackgroundScheduler;
import org.singularux.contacts.feature.contactview.domain.GetContactPermissionsUseCase;
import org.singularux.contacts.feature.contactview.domain.ListenContactUseCase;
import org.singularux.contacts.feature.contactview.domain.SetStarredUseCase;
import org.singularux.contacts.feature.contactview.ui.item.ItemContact;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.Getter;

@HiltViewModel(assistedFactory = ContactViewViewModelFactory.class)
public class ContactViewViewModel extends ViewModel {

    private final String lookupKey;
    private final @Getter String[] getContactPermissions;

    private final SetStarredUseCase setStarredUseCase;

    private final @Getter LiveData<ItemContact> itemContactLiveData;

    @AssistedInject
    public ContactViewViewModel(@Assisted String lookupKey,
                                @BackgroundScheduler Scheduler backgroundScheduler,
                                GetContactPermissionsUseCase getContactPermissionsUseCase,
                                SetStarredUseCase setStarredUseCase,
                                ListenContactUseCase listenContactUseCase) {
        this.lookupKey = lookupKey;
        this.getContactPermissions = getContactPermissionsUseCase.get();
        this.setStarredUseCase = setStarredUseCase;
        this.itemContactLiveData = LiveDataReactiveStreams
                .fromPublisher(listenContactUseCase.get(lookupKey)
                        .observeOn(backgroundScheduler)
                        .map(new Transformations.IItemContact()));
    }

    public void addToFavorites() {
        setStarredUseCase.set(lookupKey, true);
    }

    public void removeFromFavorites() {
        setStarredUseCase.set(lookupKey, false);
    }

    public void delete() {

    }

}
