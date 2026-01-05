package org.singularux.contacts.domain;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import org.singularux.contacts.core.IOScheduler;
import org.singularux.contacts.data.ContactBriefEntity;
import org.singularux.contacts.data.ContactsRepository;
import org.singularux.contacts.data.ForwardingContentObserver;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
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
                    val observer = new ForwardingContentObserver(emitter);
                    context.getContentResolver().registerContentObserver(
                            ContactsContract.Contacts.CONTENT_URI, true, observer);
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

}
