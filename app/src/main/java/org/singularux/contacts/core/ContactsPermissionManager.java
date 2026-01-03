package org.singularux.contacts.core;

import androidx.annotation.NonNull;

import java.util.Arrays;

public interface ContactsPermissionManager {

    boolean hasPermission(@NonNull ContactsPermission permission);

    default boolean hasAllPermissions(@NonNull ContactsPermission... permissions) {
        return Arrays.stream(permissions).allMatch(this::hasPermission);
    }

    @NonNull String getPermissionString(@NonNull ContactsPermission permission);

    default @NonNull String[] getPermissionStrings(@NonNull ContactsPermission... permissions) {
        return Arrays.stream(permissions)
                .map(this::getPermissionString)
                .toArray(String[]::new);
    }

}
