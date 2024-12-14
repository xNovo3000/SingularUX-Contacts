package org.singularux.contacts.data.profile.repository

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
import org.singularux.contacts.data.profile.entity.ProfileBriefEntity
import javax.inject.Inject

class ProfileBriefRepositoryAndroid @Inject constructor(
    private val context: Context,
    private val permissionManager: PermissionManager,
    private val contactsObserver: ContactsObserver
) : ProfileBriefRepository {

    companion object {
        private const val TAG = "ProfileRepositoryAndroid"
    }

    override suspend fun getProfile(): ProfileBriefEntity? {
        // Check for permission
        if (!permissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.i(TAG, "getAll(): permission denied, cannot retrieve profile")
            return null
        }
        // Read
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                ContactsContract.Profile.CONTENT_URI,
                arrayOf(
                    ContactsContract.Profile.LOOKUP_KEY,
                    ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
                    ContactsContract.Profile.PHOTO_THUMBNAIL_URI
                ),
                null, null
            ).use { cursor ->
                withContext(Dispatchers.Default) {
                    if (cursor?.moveToNext() == true) {
                        ProfileBriefEntity(
                            lookupKey = cursor.getString(0),
                            displayName = cursor.getString(1),
                            thumbnailUri = cursor.getStringOrNull(2)
                        )
                    }
                }
            }
        }
        // Not present
        return null
    }

    override fun listenProfile(): Flow<ProfileBriefEntity?> {
        return callbackFlow {
            val listener = object : ContactsObserver.Listener {
                override suspend fun onUpdate() {
                    trySendBlocking(getProfile())
                        .onSuccess { Log.d(TAG, "listenProfile(): updated profile") }
                        .onFailure { Log.e(TAG, "listenProfile(): failed to update profile") }
                }
            }
            // Subscribe at start
            contactsObserver.attachListener(listener)
            // Force first event
            trySendBlocking(getProfile())
            // Unsubscribe at the end
            awaitClose { contactsObserver.detachListener(listener) }
        }
    }

}