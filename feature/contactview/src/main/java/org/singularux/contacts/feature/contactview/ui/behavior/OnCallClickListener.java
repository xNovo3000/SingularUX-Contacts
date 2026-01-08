package org.singularux.contacts.feature.contactview.ui.behavior;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class OnCallClickListener implements View.OnClickListener {

    private final String phoneNumber;

    @Override
    public void onClick(@NonNull View v) {
        // Build URI
        val uri = new Uri.Builder()
                .scheme("tel")
                .appendEncodedPath(phoneNumber)
                .build();

        val intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);

        v.getContext().startActivity(intent);
    }

}
