package org.singularux.contacts.data;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.Collection;

import io.reactivex.rxjava3.core.FlowableEmitter;

public class ForwardingContentObserver extends ContentObserver {

    private final FlowableEmitter<Object> emitter;
    private final Object dummy = new Object();

    public ForwardingContentObserver(FlowableEmitter<Object> emitter) {
        super(new Handler(Looper.getMainLooper()));
        this.emitter = emitter;
    }

    @Override
    public void onChange(boolean selfChange) {
        // This method runs on the application main loop
        // It MUST be as fast as possible
        emitter.onNext(dummy);
    }

    @Override
    public void onChange(boolean selfChange, @NonNull Collection<Uri> uris, int flags) {
        // Always call onChange only one time
        onChange(selfChange);
    }

}
