package org.singularux.contacts.feature.contactlist.domain;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import org.singularux.contacts.core.threading.IOScheduler;
import org.singularux.contacts.data.contacts.DataContactsUri;
import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.data.contacts.repository.ContactsRepository;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.val;

public class ListenContactListUseCase {

    private final Context context;
    private final Scheduler ioScheduler;
    private final ContactsRepository contactsRepository;

    @Inject
    public ListenContactListUseCase(@ApplicationContext Context context,
                                    @IOScheduler Scheduler ioScheduler,
                                    ContactsRepository contactsRepository) {
        this.context = context;
        this.ioScheduler = ioScheduler;
        this.contactsRepository = contactsRepository;
    }

    public @NonNull Flowable<List<ContactBriefEntity>> get() {
        return Flowable
                .create(emitter -> {
                    // Start observing
                    val observer = new ForwardingRxContentObserver(emitter);
                    context.getContentResolver().registerContentObserver(
                            DataContactsUri.ofContactList(), false, observer);
                    // Force first update
                    observer.onChange(false);
                    // Unregister when not needed anymore
                    emitter.setCancellable(() -> context.getContentResolver()
                            .unregisterContentObserver(observer));
                }, BackpressureStrategy.LATEST)
                .subscribeOn(AndroidSchedulers.mainThread(), false)
                .observeOn(ioScheduler)
                .map(o -> contactsRepository.getAll());
    }

    private static class ForwardingRxContentObserver extends ContentObserver {

        private final FlowableEmitter<Object> emitter;
        private final Object dummy = new Object();

        public ForwardingRxContentObserver(FlowableEmitter<Object> emitter) {
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

}
