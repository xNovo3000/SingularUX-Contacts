package org.singularux.contacts.data.contacts

import android.net.Uri
import android.provider.ContactsContract

object DataContactsCommon {
    fun getContactListUri(): Uri = ContactsContract.Contacts.CONTENT_URI
}