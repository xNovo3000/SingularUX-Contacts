package org.singularux.contacts.viewmodel

import android.Manifest
import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.singularux.contacts.observer.ContactListObserver
import org.singularux.contacts.model.ContactItem

class ContactListViewModel(app: Application) : AndroidViewModel(app) {

    /* Mutable data */
    private val givenPermissions = mutableSetOf<String>()
    private val mutableContactList = MutableStateFlow<List<ContactItem>>(listOf())

    /* Observers */
    private val contactListObserver = ContactListObserver(app.applicationContext, mutableContactList)

    /* Functions */

    suspend fun onGivenPermission(permission: String) {
        // Always do these operations in the ViewModel context
        withContext(viewModelScope.coroutineContext) {
            // Set permission as given
            givenPermissions.add(permission).also {
                if (it) {
                    when (permission) {
                        Manifest.permission.READ_CONTACTS -> onReadContactsPermissionGiven()
                    }
                }
            }
        }
    }

    private fun onReadContactsPermissionGiven() {
        // Register the content observer
        getApplication<Application>().contentResolver.registerContentObserver(
            ContactsContract.Contacts.CONTENT_URI,
            false,
            contactListObserver
        )
        // Load contacts
        contactListObserver.dispatchChange(false, null)
    }

    override fun onCleared() {
        Log.d("Viewmodel", "Oncleared called")
        // Unregister content observer (also if has not been registered)
        getApplication<Application>().contentResolver.unregisterContentObserver(contactListObserver)
    }

    /* Observer */
    val contactList: StateFlow<List<ContactItem>> = mutableContactList.asStateFlow()

}