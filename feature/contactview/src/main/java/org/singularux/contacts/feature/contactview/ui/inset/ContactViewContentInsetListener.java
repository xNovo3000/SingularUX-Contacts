package org.singularux.contacts.feature.contactview.ui.inset;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import lombok.val;

public class ContactViewContentInsetListener implements OnApplyWindowInsetsListener {

    private static final String TAG = "ContactViewContentInsetListener";

    @Override
    public @NonNull WindowInsetsCompat onApplyWindowInsets(
            @NonNull View view,
            @NonNull WindowInsetsCompat windowInsets
    ) {
        Log.d(TAG, "WindowInsets received, updating paddings");
        // Get information about metrics and insets
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() |
                WindowInsetsCompat.Type.displayCutout());
        // Update padding
        view.setPadding(insets.left, 0, insets.right, insets.bottom);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
