package org.singularux.contacts.feature.contactview.ui.behavior;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class OnMessageClickListener implements View.OnClickListener {

    private final String phoneNumber;

    @Override
    public void onClick(@NonNull View v) {
        val context = v.getContext();
        // Build URI
        val uri = new Uri.Builder()
                .scheme("smsto")
                .appendEncodedPath(phoneNumber)
                .build();
        // Build intent
        val intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(uri);
        // Launch activity
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
