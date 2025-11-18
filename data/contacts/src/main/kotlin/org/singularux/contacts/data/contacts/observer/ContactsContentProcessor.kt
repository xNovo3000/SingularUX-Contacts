package org.singularux.contacts.data.contacts.observer

import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactsContentProcessor(
    private val coroutineScope: CoroutineScope
) : ContentObserver(Handler(Looper.getMainLooper())) {

    private var job: Job? = null
    private val observers = mutableListOf<ContactsContentObserver>()
    
    override fun onChange(selfChange: Boolean) {
        job?.cancel()
        job = coroutineScope.launch {
            synchronized(observers) { observers.toList() }
                .forEach { it.onChange() }
        }
    }
    
    fun registerObserver(observer: ContactsContentObserver) {
        synchronized(observers) {
            observers.add(observer)
        }
    }
    
    fun unregisterObserver(observer: ContactsContentObserver) {
        synchronized(observers) {
            observers.remove(observer)
        }
    }

}