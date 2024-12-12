package org.singularux.contacts.data.contacts

import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ContactsObserver : ContentObserver(Handler(Looper.getMainLooper())) {
    
    companion object {
        private const val TAG = "ContactsObserver"
        val LISTEN_URI: Uri = ContactsContract.Contacts.CONTENT_URI
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var currentUpdateJob: Job? = null

    override fun onChange(selfChange: Boolean) {
        Log.d(TAG, "onChange#0(): received a change event from system")
        // Signal all subscribed functions
        currentUpdateJob?.cancel()
        currentUpdateJob = coroutineScope.launch {
            for (listener in listeners) {
                listener.onUpdate()
            }
        }
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        Log.d(TAG, "onChange#1(): received a change event from system using URI ${uri?.toString()}")
        super.onChange(selfChange, uri)
    }

    override fun onChange(selfChange: Boolean, uris: MutableCollection<Uri>, flags: Int) {
        // Call once
        onChange(selfChange)
    }

    /* Listen to android update using coroutines */

    interface Listener {
        suspend fun onUpdate()
    }

    private val listeners = mutableListOf<Listener>()

    fun attachListener(listener: Listener) {
        listeners.add(listener)
    }

    fun detachListener(listener: Listener) {
        listeners.remove(listener)
    }

}