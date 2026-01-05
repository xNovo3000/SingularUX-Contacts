package org.singularux.contacts.ui;

import android.util.Log;
import android.view.View;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import org.jspecify.annotations.NonNull;

import lombok.val;

public class ContactListRecyclerViewInsetListener implements OnApplyWindowInsetsListener {

    private static final String TAG = "ContactListRecyclerViewInsetListener";

    public static final int MARGIN_TOP_DP = 72;
    public static final int MARGIN_BOTTOM_DP = 112;

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
        val left = insets.left;
        val right = insets.right;
        val top = insets.top + (int) (density * MARGIN_TOP_DP);
        val bottom = insets.bottom + (int) (density * MARGIN_BOTTOM_DP);
        view.setPadding(left, top, right, bottom);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
