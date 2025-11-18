package org.singularux.contacts.data.contacts.observer

import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.singularux.contacts.core.permission.ContactsPermission
import org.singularux.contacts.core.permission.ContactsPermissionManager
import org.singularux.contacts.data.contacts.repository.ContactsRepositoryAndroid

class ContactsContentLifecycleObserver(
    private val context: Context,
    private val contactsContentProcessor: ContactsContentProcessor,
    private val contactsPermissionManager: ContactsPermissionManager
) : DefaultLifecycleObserver {

    companion object {
        private const val TAG = "ContactsContentLifecycleObserver"
    }

    override fun onResume(owner: LifecycleOwner) {
        if (contactsPermissionManager.hasPermissions(ContactsPermission.READ_CONTACTS)) {
            Log.d(TAG, "Registering processor and launching first update")
            context.contentResolver.registerContentObserver(ContactsRepositoryAndroid.URI,
                true, contactsContentProcessor)
            contactsContentProcessor.onChange(false)
        } else {
            Log.d(TAG, "Missing permissions READ_CONTACTS")
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d(TAG, "Unregistering processor and launching first update")
        context.contentResolver.unregisterContentObserver(contactsContentProcessor)
    }

}