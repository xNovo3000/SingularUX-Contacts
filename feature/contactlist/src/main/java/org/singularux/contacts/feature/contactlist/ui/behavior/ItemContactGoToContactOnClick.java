package org.singularux.contacts.feature.contactlist.ui.behavior;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;

import androidx.annotation.NonNull;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class ItemContactGoToContactOnClick implements View.OnClickListener {

    private final String lookupKey;

    @Override
    public void onClick(@NonNull View v) {
        val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
        val intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
            v.getContext().startActivity(intent);
        }
    }

}
