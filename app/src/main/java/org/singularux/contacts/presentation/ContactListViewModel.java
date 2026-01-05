package org.singularux.contacts.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import org.singularux.contacts.core.BackgroundScheduler;
import org.singularux.contacts.core.ContactsPermission;
import org.singularux.contacts.core.ContactsPermissionManager;
import org.singularux.contacts.domain.ListenContactListUseCase;
import org.singularux.contacts.ui.ComponentData;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.Getter;
import lombok.val;

@HiltViewModel
public class ContactListViewModel extends ViewModel {

    private final @Getter String[] readContactsPermissions;
    private final @Getter LiveData<List<ComponentData>> contactListLiveData;

    @Inject
    public ContactListViewModel(ContactsPermissionManager contactsPermissionManager,
                                ListenContactListUseCase listenContactListUseCase,
                                @BackgroundScheduler Scheduler backgroundScheduler) {
        this.readContactsPermissions = contactsPermissionManager.getPermissionStrings(
                ContactsPermission.READ_CONTACTS, ContactsPermission.READ_PROFILE);
        val contactListFlowable = listenContactListUseCase.get()
                .observeOn(backgroundScheduler)
                .map(list -> list.stream()
                        .map(new ContactBriefEntityTransformations.Standard())
                        .collect(Collectors.toList()));
        this.contactListLiveData = LiveDataReactiveStreams
                .fromPublisher(contactListFlowable);
    }

}
