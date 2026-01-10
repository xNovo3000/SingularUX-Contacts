package org.singularux.contacts.feature.contactlist.ui.inset;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import lombok.val;

public class ContactListSelectionToolbarInsetListener implements OnApplyWindowInsetsListener {

    private static final String TAG = "ContactListSelectionAppBarInsetListener";

    @Override
    public @NonNull WindowInsetsCompat onApplyWindowInsets(
            @NonNull View view,
            @NonNull WindowInsetsCompat windowInsets
    ) {
        Log.d(TAG, "WindowInsets received, updating margins");
        // Get information about metrics and insets
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() |
                WindowInsetsCompat.Type.displayCutout());
        // Update margins
        val marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = insets.top;
        marginLayoutParams.bottomMargin = 0;
        marginLayoutParams.leftMargin = insets.left;
        marginLayoutParams.rightMargin = insets.right;
        view.setLayoutParams(marginLayoutParams);
        // All insets were consumed
        return WindowInsetsCompat.CONSUMED;
    }

}
