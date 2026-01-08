package org.singularux.contacts.feature.contactview.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import org.singularux.contacts.data.contacts.entity.ContactEntity;
import org.singularux.contacts.feature.contactview.domain.GetReadContactsPermissionsUseCase;
import org.singularux.contacts.feature.contactview.domain.ListenContactUseCase;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel(assistedFactory = ContactViewViewModelFactory.class)
public class ContactViewViewModel extends ViewModel {

    private final String[] readContactPermissions;

    private final @Getter LiveData<ContactEntity> contactEntityLiveData;

    @AssistedInject
    public ContactViewViewModel(@Assisted String lookupKey,
                                GetReadContactsPermissionsUseCase getReadContactsPermissionsUseCase,
                                ListenContactUseCase listenContactUseCase) {
        this.readContactPermissions = getReadContactsPermissionsUseCase.get();
        this.contactEntityLiveData = LiveDataReactiveStreams
                .fromPublisher(listenContactUseCase.get(lookupKey));
    }

}
