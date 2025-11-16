package org.singularux.contacts.feature.contactlist.domain

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.singularux.contacts.data.contacts.DataContactsCommon
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity
import org.singularux.contacts.data.contacts.repository.ContactsRepository
import javax.inject.Inject

class ListenContactListUseCase @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val contactsRepository: ContactsRepository
) {

    companion object {
        private const val TAG = "ListenContactListUseCase"
    }

    operator fun invoke(): Flow<List<ContactBriefEntity>> = callbackFlow {
        // Create observer
        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            private val job: Job? = null
            override fun onChange(selfChange: Boolean) {
                job?.cancel()
                launch {
                    trySend(element = contactsRepository.getAll())
                        .onSuccess { Log.d(TAG, "Sent") }
                        .onFailure { Log.e(TAG, "Failed to send", it) }
                }
            }
        }
        // Register observer
        context.contentResolver.registerContentObserver(
            DataContactsCommon.getContactListUri(), false, observer)
        // Force first update when connected
        observer.onChange(false)
        // Unregister on flow close
        awaitClose { context.contentResolver.unregisterContentObserver(observer) }
    }

}