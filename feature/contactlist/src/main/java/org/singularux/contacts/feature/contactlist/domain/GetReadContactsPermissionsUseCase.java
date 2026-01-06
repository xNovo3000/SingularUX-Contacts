package org.singularux.contacts.feature.contactlist.domain;

import org.singularux.contacts.core.permission.ContactsPermission;
import org.singularux.contacts.core.permission.ContactsPermissionManager;

import javax.inject.Inject;

public class GetReadContactsPermissionsUseCase {

    private final ContactsPermissionManager contactsPermissionManager;

    @Inject
    public GetReadContactsPermissionsUseCase(ContactsPermissionManager contactsPermissionManager) {
        this.contactsPermissionManager = contactsPermissionManager;
    }

    public String[] get() {
        return contactsPermissionManager.getPermissionStrings(ContactsPermission.READ_CONTACTS,
                ContactsPermission.READ_PROFILE);
    }

}
