package org.singularux.contacts.core.permission

interface PermissionManager {
    fun hasPermission(permission: ContactsPermission): Boolean
    fun getPermissionString(permission: ContactsPermission): String
    fun getPermissionsString(permissions: List<ContactsPermission>): List<String>
}