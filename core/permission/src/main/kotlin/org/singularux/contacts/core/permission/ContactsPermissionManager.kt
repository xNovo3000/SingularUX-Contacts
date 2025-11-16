package org.singularux.contacts.core.permission

interface ContactsPermissionManager {

    fun hasPermission(permission: ContactsPermission): Boolean
    fun getPermissionString(permission: ContactsPermission): String

    fun hasPermissions(vararg permissions: ContactsPermission): List<Boolean> {
        return permissions.map { hasPermission(it) }
    }

    fun getPermissionsString(vararg permissions: ContactsPermission): List<String> {
        return permissions.map { getPermissionString(it) }
    }

}