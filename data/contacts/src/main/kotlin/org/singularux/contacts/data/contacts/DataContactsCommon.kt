package org.singularux.contacts.data.contacts

import android.net.Uri
import android.provider.ContactsContract
import androidx.core.net.toUri

object DataContactsCommon {

    fun getContactListUri(): Uri = ContactsContract.Contacts.CONTENT_URI

    fun getContactDetailUri(
        lookupKey: String
    ): Uri = "${ContactsContract.Contacts.CONTENT_LOOKUP_URI}/$lookupKey".toUri()

}