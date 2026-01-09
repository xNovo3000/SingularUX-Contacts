package org.singularux.contacts.feature.contactview.domain;

import org.singularux.contacts.core.permission.ContactsPermission;
import org.singularux.contacts.core.permission.ContactsPermissionManager;

import javax.inject.Inject;

public class GetContactsPermissionUseCase {

    private final ContactsPermissionManager contactsPermissionManager;

    @Inject
    public GetContactsPermissionUseCase(ContactsPermissionManager contactsPermissionManager) {
        this.contactsPermissionManager = contactsPermissionManager;
    }

    public String[] get() {
        return contactsPermissionManager.getPermissionStrings(
                ContactsPermission.READ_CONTACTS,
                ContactsPermission.READ_PROFILE,
                ContactsPermission.WRITE_CONTACTS,
                ContactsPermission.WRITE_PROFILE);
    }

}
