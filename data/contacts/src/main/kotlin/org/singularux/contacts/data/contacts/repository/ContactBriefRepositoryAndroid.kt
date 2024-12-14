package org.singularux.contacts.data.contacts.repository

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.core.database.getStringOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.singularux.contacts.core.permission.ContactsPermission
import org.singularux.contacts.core.permission.PermissionManager
import org.singularux.contacts.data.contacts.ContactsObserver
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity

internal class ContactBriefRepositoryAndroid(
    private val context: Context,
    private val permissionManager: PermissionManager,
    private val contactsObserver: ContactsObserver
) : ContactBriefRepository {

    companion object {
        private const val TAG = "ContactBriefRepositoryAndroid"
    }

    override suspend fun getAll(): List<ContactBriefEntity> {
        // Check for permission
        if (!permissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.i(TAG, "getAll(): permission denied, cannot retrieve contacts")
            return emptyList()
        }
        // Read
        val result = mutableListOf<ContactBriefEntity>()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                arrayOf(
                    ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                    ContactsContract.Contacts.PHOTO_THUMBNAIL_URI, ContactsContract.Contacts.STARRED
                ),
                "${ContactsContract.Contacts.IN_VISIBLE_GROUP} = ?",
                arrayOf("1"),
                ContactsContract.Contacts.SORT_KEY_PRIMARY
            ).use { cursor ->
                withContext(Dispatchers.Default) {
                    while (cursor?.moveToNext() == true) {
                        result.add(
                            ContactBriefEntity(
                                lookupKey = cursor.getString(0),
                                displayName = cursor.getString(1),
                                thumbnailUri = cursor.getStringOrNull(2),
                                isStarred = cursor.getInt(3) != 0
                            )
                        )
                    }
                }
            }
        }
        return result
    }

    override fun listenAll(): Flow<List<ContactBriefEntity>> {
        return callbackFlow {
            val listener = object : ContactsObserver.Listener {
                override suspend fun onUpdate() {
                    trySendBlocking(getAll())
                        .onSuccess { Log.d(TAG, "listenAll(): updated contact list") }
                        .onFailure { Log.e(TAG, "listenAll(): failed to update contact list") }
                }
            }
            // Subscribe at start
            contactsObserver.attachListener(listener)
            // Force push first event
            trySendBlocking(getAll())
            // Unsubscribe at the end
            awaitClose { contactsObserver.detachListener(listener) }
        }
    }

}