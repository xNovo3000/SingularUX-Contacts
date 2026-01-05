package org.singularux.contacts.ui;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import org.jspecify.annotations.NonNull;

import lombok.val;

public class ContactListFabInsetListener implements OnApplyWindowInsetsListener {

    private static final String TAG = "ContactListFabInsetListener";

    public static final int MARGIN_DP = 16;

    @Override
    public @NonNull WindowInsetsCompat onApplyWindowInsets(
            @NonNull View view,
            @NonNull WindowInsetsCompat windowInsets
    ) {
        Log.d(TAG, "WindowInsets received, updating margins");
        // Get information about metrics and insets
        val density = view.getResources().getDisplayMetrics().density;
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
        // Update margins
        val marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = (int) (density * MARGIN_DP);
        marginLayoutParams.bottomMargin = insets.bottom + (int) (density * MARGIN_DP);
        marginLayoutParams.leftMargin = insets.left + (int) (density * MARGIN_DP);
        marginLayoutParams.rightMargin = insets.right + (int) (density * MARGIN_DP);
        view.setLayoutParams(marginLayoutParams);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
