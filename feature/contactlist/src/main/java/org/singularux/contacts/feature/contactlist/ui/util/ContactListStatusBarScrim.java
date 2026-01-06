package org.singularux.contacts.feature.contactlist.ui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactListStatusBarScrim extends View {

    private int statusBarHeightPx = 0;

    public ContactListStatusBarScrim(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeStatusBarSizeListener();
    }

    public ContactListStatusBarScrim(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeStatusBarSizeListener();
    }

    public ContactListStatusBarScrim(Context context) {
        super(context);
        initializeStatusBarSizeListener();
    }

    private void initializeStatusBarSizeListener() {
        ViewCompat.setOnApplyWindowInsetsListener(this, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            statusBarHeightPx = systemInsets.top;
            requestLayout();
            return insets;
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set measured dimensions: width = match_parent, height = status bar height
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(parentWidth, statusBarHeightPx);
    }

}
