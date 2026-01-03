package org.singularux.contacts.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import org.singularux.contacts.core.ContactsPermission;
import org.singularux.contacts.core.ContactsPermissionManager;
import org.singularux.contacts.data.ContactBriefEntity;
import org.singularux.contacts.domain.ListenContactListUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class ContactListViewModel extends ViewModel {

    private final @Getter String[] readContactsPermissions;
    private final @Getter LiveData<List<ComponentData>> contactListLiveData;

    @Inject
    public ContactListViewModel(ContactsPermissionManager contactsPermissionManager,
                                ListenContactListUseCase listenContactListUseCase
    ) {
        this.readContactsPermissions = contactsPermissionManager.getPermissionStrings(
                ContactsPermission.READ_CONTACTS, ContactsPermission.READ_PROFILE);
        this.contactListLiveData = LiveDataReactiveStreams
                .fromPublisher(listenContactListUseCase.get());
    }

}
