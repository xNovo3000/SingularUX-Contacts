package org.singularux.contacts

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class ContactsViewModel(app: Application) : AndroidViewModel(app) {

    /* Mutable data */
    private val givenPermissions = mutableListOf<String>()
    private val mutableContactList = MutableStateFlow<List<ContactsModel.ContactItem>>(listOf())

    /* Observers */
    private val contactsObserver = ContactsObserver(app.applicationContext, mutableContactList)

    /* Functions */

    suspend fun onGivenPermission(permission: String) {
        // Always do these operations in the ViewModel context
        withContext(viewModelScope.coroutineContext) {
            // Check if already given
            if (givenPermissions.contains(permission)) {
                return@withContext
            }
            // Set permission as given
            givenPermissions.add(permission)
            // Register content observer
            getApplication<Application>().contentResolver.registerContentObserver(
                ContactsContract.Contacts.CONTENT_URI,
                false,
                contactsObserver
            )
        }
    }

    override fun onCleared() {
        // Unregister content observer (also if has not been registered)
        getApplication<Application>().contentResolver.unregisterContentObserver(contactsObserver)
    }

    /* Observer */
    val contactList: StateFlow<List<ContactsModel.ContactItem>> = mutableContactList

}