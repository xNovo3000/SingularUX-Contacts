package org.singularux.contacts.feature.contactlist.ui.inset;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import lombok.val;

public class ContactListSearchBarInsetListener implements OnApplyWindowInsetsListener {

    private static final String TAG = "ContactListSearchBarInsetListener";

    public static final int MARGIN_HORIZONTAL_DP = 16;
    public static final int MARGIN_VERTICAL_DP = 8;

    @Override
    public @NonNull WindowInsetsCompat onApplyWindowInsets(
            @NonNull View view,
            @NonNull WindowInsetsCompat windowInsets
    ) {
        Log.d(TAG, "WindowInsets received, updating margins");
        // Get information about metrics and insets
        val density = view.getResources().getDisplayMetrics().density;
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() |
                WindowInsetsCompat.Type.displayCutout());
        // Update margins
        val marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = insets.top + (int) (density * MARGIN_VERTICAL_DP);
        marginLayoutParams.bottomMargin = (int) (density * MARGIN_VERTICAL_DP);
        marginLayoutParams.leftMargin = insets.left + (int) (density * MARGIN_HORIZONTAL_DP);
        marginLayoutParams.rightMargin = insets.right + (int) (density * MARGIN_HORIZONTAL_DP);
        view.setLayoutParams(marginLayoutParams);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
