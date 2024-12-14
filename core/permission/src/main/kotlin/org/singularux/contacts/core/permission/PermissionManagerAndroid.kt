package org.singularux.contacts.core.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

internal class PermissionManagerAndroid(
    private val context: Context
) : PermissionManager {

    override fun hasPermission(permission: ContactsPermission): Boolean {
        return ContextCompat.checkSelfPermission(
            context, getPermissionString(permission)
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun getPermissionString(permission: ContactsPermission): String {
        return when (permission) {
            ContactsPermission.READ_CONTACTS -> Manifest.permission.READ_CONTACTS
            ContactsPermission.WRITE_CONTACTS -> Manifest.permission.WRITE_CONTACTS
            ContactsPermission.READ_PROFILE -> Manifest.permission.GET_ACCOUNTS
            ContactsPermission.WRITE_PROFILE -> TODO()
        }
    }

}