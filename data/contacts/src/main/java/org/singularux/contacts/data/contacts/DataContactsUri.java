package org.singularux.contacts.data.contacts;

import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

public class DataContactsUri {

    private DataContactsUri() {}

    public static @NonNull Uri ofContactList() {
        return ContactsContract.Contacts.CONTENT_URI;
    }

    public static @NonNull Uri ofContact(@NonNull String lookupKey) {
        return Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
    }

}
