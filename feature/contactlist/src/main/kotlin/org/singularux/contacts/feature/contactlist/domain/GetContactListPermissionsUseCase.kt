package org.singularux.contacts.feature.contactlist.domain

import org.singularux.contacts.core.permission.ContactsPermission
import org.singularux.contacts.core.permission.ContactsPermissionManager
import javax.inject.Inject

class GetContactListPermissionsUseCase @Inject constructor(
    private val contactsPermissionManager: ContactsPermissionManager
) {

    operator fun invoke(): List<String> {
        return contactsPermissionManager.getPermissionsString(
            ContactsPermission.READ_CONTACTS
        )
    }

}