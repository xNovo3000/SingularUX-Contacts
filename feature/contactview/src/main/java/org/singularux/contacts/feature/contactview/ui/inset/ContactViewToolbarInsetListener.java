package org.singularux.contacts.feature.contactview.ui.inset;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
        Log.d(TAG, "WindowInsets received, updating margins");
        // Get information about metrics and insets
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
        // Update padding
        val layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(insets.left, insets.top, insets.right, 0);
        view.setLayoutParams(layoutParams);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
