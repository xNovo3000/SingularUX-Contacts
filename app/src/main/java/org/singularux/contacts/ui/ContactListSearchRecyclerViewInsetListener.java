package org.singularux.contacts.ui;

import android.view.View;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import org.jspecify.annotations.NonNull;

import lombok.val;

public class ContactListSearchRecyclerViewInsetListener implements OnApplyWindowInsetsListener {

    @Override
    public @NonNull WindowInsetsCompat onApplyWindowInsets(
            @NonNull View view,
            @NonNull WindowInsetsCompat windowInsets
    ) {
        // Get information about insets
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
        // Update padding
        view.setPadding(insets.left, 0, insets.right, insets.bottom);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
