package org.singularux.contacts.data.contacts.repository

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.core.database.getStringOrNull
import androidx.core.net.toUri
import org.singularux.contacts.core.permission.ContactsPermission
import org.singularux.contacts.core.permission.ContactsPermissionManager
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity

internal class ContactsRepositoryAndroid(
    private val context: Context,
    private val contactsPermissionManager: ContactsPermissionManager
) : ContactsRepository {

    companion object {
        private const val TAG = "ContactsRepositoryAndroid"

        val URI: Uri = ContactsContract.Contacts.CONTENT_URI
        private val GET_ALL_PROJECTION = arrayOf(
            ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI, ContactsContract.Contacts.STARRED
        )
        private const val GET_ALL_SELECTION =
            "${ContactsContract.Contacts.IN_VISIBLE_GROUP} = ? AND " +
                    "${ContactsContract.Contacts.IS_USER_PROFILE} = ?"
        private val GET_ALL_SELECTION_ARGS = arrayOf("1", "0")
        private const val GET_ALL_SORT_ORDER = ContactsContract.Contacts.SORT_KEY_PRIMARY
    }

    override suspend fun getAll(): List<ContactBriefEntity> {
        // Check permissions
        if (!contactsPermissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.d(TAG, "Missing READ_CONTACTS permission")
            return emptyList()
        }
        // Query android provider
        return context.contentResolver.query(
            URI, GET_ALL_PROJECTION, GET_ALL_SELECTION,
            GET_ALL_SELECTION_ARGS, GET_ALL_SORT_ORDER
        ).use { cursor ->
            val result = mutableListOf<ContactBriefEntity>()
            while (cursor?.moveToNext() == true) {
                result.add(
                    element = ContactBriefEntity(
                        lookupKey = cursor.getString(0),
                        displayName = cursor.getString(1),
                        photoThumbnailUri = cursor.getStringOrNull(2)?.toUri(),
                        isStarred = cursor.getInt(3) != 0
                    )
                )
            }
            result
        }
    }

}