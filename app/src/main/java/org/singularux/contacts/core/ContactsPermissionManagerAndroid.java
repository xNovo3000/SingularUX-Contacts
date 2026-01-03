package org.singularux.contacts.core;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactsPermissionManagerAndroid implements ContactsPermissionManager {

    private final Context context;

    @Override
    public boolean hasPermission(@NonNull ContactsPermission permission) {
        return ContextCompat.checkSelfPermission(context, getPermissionString(permission)) ==
                PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public @NonNull String getPermissionString(@NonNull ContactsPermission permission) {
        switch (permission) {
            case READ_CONTACTS:
                return Manifest.permission.READ_CONTACTS;
            case READ_PROFILE:
                return "android.permission.READ_PROFILE";
            case WRITE_CONTACTS:
                return Manifest.permission.WRITE_CONTACTS;
            case WRITE_PROFILE:
                return "android.permission.WRITE_PROFILE";
            default:
                throw new IllegalArgumentException("Wrong permission enum: " + permission);
        }
    }

}
