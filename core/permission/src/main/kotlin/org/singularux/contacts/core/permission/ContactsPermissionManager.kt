package org.singularux.contacts.core.permission

interface ContactsPermissionManager {
    fun hasPermission(permission: ContactsPermission): Boolean
    fun getPermissionString(permission: ContactsPermission): String
}