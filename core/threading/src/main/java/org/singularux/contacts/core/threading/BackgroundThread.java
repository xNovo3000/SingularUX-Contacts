package org.singularux.contacts.core.threading;

import android.os.Process;

public class BackgroundThread extends Thread {

    public BackgroundThread(Runnable r, String name) {
        super(r, name);
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        super.run();
    }

}
