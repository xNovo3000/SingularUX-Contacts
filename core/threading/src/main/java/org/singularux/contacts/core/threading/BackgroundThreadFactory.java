package org.singularux.contacts.core.threading;

import android.util.Log;

import java.util.concurrent.ThreadFactory;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class BackgroundThreadFactory implements ThreadFactory {

    private static final String TAG = "BackgroundThreadFactory";

    private final String factoryName;

    private int ordinal = -1;

    @Override
    public Thread newThread(Runnable r) {
        // Generate variables
        ordinal += 1;
        val name = factoryName + '-' + ordinal;
        // Create thread
        Log.d(TAG, "Starting thread " + name);
        return new BackgroundThread(r, name);
    }

}
