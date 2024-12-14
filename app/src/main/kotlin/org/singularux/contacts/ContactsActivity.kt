package org.singularux.contacts

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import org.singularux.contacts.core.permission.ContactsPermission
import org.singularux.contacts.core.permission.PermissionManager
import org.singularux.contacts.data.contacts.ContactsObserver
import javax.inject.Inject

@AndroidEntryPoint
class ContactsActivity : FragmentActivity(R.layout.activity_contacts) {

    companion object {
        private const val TAG = "ContactsActivity"
    }

    @Inject lateinit var permissionManager: PermissionManager
    @Inject lateinit var contactsObserver: ContactsObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        // Observe data
        if (permissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.d(TAG, "onResume(): registered ContactsObserver")
            applicationContext.contentResolver.registerContentObserver(
                ContactsObserver.LISTEN_URI, false, contactsObserver
            )
        }
    }

    override fun onPause() {
        super.onPause()
        // Stop observing data
        if (permissionManager.hasPermission(ContactsPermission.READ_CONTACTS)) {
            Log.d(TAG, "onPause(): deregistered ContactsObserver")
            applicationContext.contentResolver.unregisterContentObserver(contactsObserver)
        }
    }

}