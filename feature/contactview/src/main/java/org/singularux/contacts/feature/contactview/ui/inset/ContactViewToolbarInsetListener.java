package org.singularux.contacts.feature.contactview.ui.inset;

import android.util.Log;
import android.view.View;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import org.jspecify.annotations.NonNull;

import lombok.val;

public class ContactViewToolbarInsetListener implements OnApplyWindowInsetsListener {

    private static final String TAG = "ContactViewToolbarInsetListener";

    @Override
    public @NonNull WindowInsetsCompat onApplyWindowInsets(
            @NonNull View view,
            @NonNull WindowInsetsCompat windowInsets
    ) {
        Log.d(TAG, "WindowInsets received, updating paddings");
        // Get information about metrics and insets
        val density = view.getResources().getDisplayMetrics().density;
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
        // Update padding
        view.setPadding(insets.left, insets.top, insets.right, 0);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
