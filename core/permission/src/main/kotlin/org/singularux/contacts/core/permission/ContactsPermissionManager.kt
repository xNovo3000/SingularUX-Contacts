package org.singularux.contacts.core.permission

interface ContactsPermissionManager {

    fun hasPermission(permission: ContactsPermission): Boolean
    fun getPermissionString(permission: ContactsPermission): String

    fun hasPermissions(vararg permissions: ContactsPermission): Boolean {
        return permissions.map { hasPermission(it) }.all { it }
    }

    fun getPermissionsString(vararg permissions: ContactsPermission): List<String> {
        return permissions.map { getPermissionString(it) }
    }

}