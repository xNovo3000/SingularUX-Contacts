package org.singularux.contacts.observer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import androidx.core.database.getStringOrNull
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.singularux.contacts.model.ContactItem

class ContactListObserver(
    private val context: Context,
    private val mutableContactList: MutableStateFlow<List<ContactItem>>
) : ContentObserver(Handler(Looper.getMainLooper())) {

    companion object {
        private val CONTACTS_PROJECTION = arrayOf(
            ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI, ContactsContract.Contacts.STARRED,
            ContactsContract.Contacts.IS_USER_PROFILE
        )
    }

    private var job: Job? = null

    override fun onChange(selfChange: Boolean) {
        // Check for permissions
        if (context.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // Launch a single job
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {
            mutableContactList.emit(reloadContactList())
        }
    }

    override fun onChange(selfChange: Boolean, uris: MutableCollection<Uri>, flags: Int) {
        // Always call onChange one time. It reloads everything
        onChange(selfChange)
    }

    private suspend fun reloadContactList(): MutableList<ContactItem> {
        return withContext(Dispatchers.IO) {
            context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                CONTACTS_PROJECTION,
                null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY
            ).use {
                val newContacts = mutableListOf<ContactItem>()
                while (it?.moveToNext() == true) {
                    newContacts.add(
                        ContactItem(
                            lookupKey = it.getString(0),
                            displayName = it.getString(1),
                            thumbnailUri = it.getStringOrNull(2),
                            isStarred = it.getInt(3) != 0,
                            isUserProfile = it.getInt(4) != 0,
                        )
                    )
                }
                newContacts
            }
        }
    }

}