package org.singularux.contacts.core;

import android.os.Process;
import android.util.Log;

import java.util.concurrent.ThreadFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BackgroundThreadFactory implements ThreadFactory {

    private static final String TAG = "BackgroundThreadFactory";

    private final String factoryName;
    private int ordinal = -1;

    @Override
    public Thread newThread(Runnable r) {
        ordinal += 1;
        return new BackgroundThread(r, factoryName + "-" + ordinal);
    }

    private static class BackgroundThread extends Thread {

        public BackgroundThread(Runnable r, String name) {
            super(r, name);
        }

        @Override
        public void run() {
            Log.d(TAG, "Starting thread '" + getName() + "'");
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            super.run();
        }

    }

}
