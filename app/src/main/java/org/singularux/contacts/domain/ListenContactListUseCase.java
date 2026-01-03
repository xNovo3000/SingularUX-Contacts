package org.singularux.contacts.domain;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import org.singularux.contacts.core.BackgroundScheduler;
import org.singularux.contacts.core.IOScheduler;
import org.singularux.contacts.data.ContactBriefEntity;
import org.singularux.contacts.data.ContactsRepository;
import org.singularux.contacts.data.ForwardingContentObserver;
import org.singularux.contacts.ui.ComponentContactData;
import org.singularux.contacts.ui.ComponentData;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.val;

public class ListenContactListUseCase {

    private final Context context;
    private final Scheduler backgroundScheduler, ioScheduler;
    private final ContactsRepository contactsRepository;

    @Inject
    public ListenContactListUseCase(@ApplicationContext Context context,
                                    @BackgroundScheduler Scheduler backgroundScheduler,
                                    @IOScheduler Scheduler ioScheduler,
                                    ContactsRepository contactsRepository) {
        this.context = context;
        this.backgroundScheduler = backgroundScheduler;
        this.ioScheduler = ioScheduler;
        this.contactsRepository = contactsRepository;
    }

    public @NonNull Flowable<List<ComponentData>> get() {
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
                .subscribeOn(backgroundScheduler)
                .observeOn(ioScheduler)
                .map(o -> contactsRepository.getAll())
                .observeOn(backgroundScheduler)
                .map(list -> list.stream()
                        .map(e -> new ComponentContactData(e.getLookupKey(), e.getDisplayName(), e.getThumbnailPath()))
                        .map(e -> (ComponentData) e)
                        .collect(Collectors.toList()));
    }

}
