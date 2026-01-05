package org.singularux.contacts.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.core.IOScheduler;
import org.singularux.contacts.data.ContactBriefEntity;
import org.singularux.contacts.data.ContactsRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;

public class ListenContactListByNameUseCase {

    private static final int DEBOUNCE_MS = 100;

    private final ContactsRepository contactsRepository;
    private final Scheduler ioScheduler;

    @Inject
    public ListenContactListByNameUseCase(ContactsRepository contactsRepository,
                                          @IOScheduler Scheduler ioScheduler) {
        this.contactsRepository = contactsRepository;
        this.ioScheduler = ioScheduler;
    }

    public Flowable<List<ContactBriefEntity>> get(@NonNull Emitter emitter) {
        return Flowable.create(emitter, BackpressureStrategy.LATEST)
                .subscribeOn(AndroidSchedulers.mainThread(), false)
                .debounce(DEBOUNCE_MS, TimeUnit.MILLISECONDS)
                .observeOn(ioScheduler)
                .map(contactsRepository::getByDisplayNameLike);
    }

    public static class Emitter implements FlowableOnSubscribe<String> {

        public @Nullable FlowableEmitter<String> emitter = null;

        @Override
        public void subscribe(@NonNull FlowableEmitter<String> emitter) {
            this.emitter = emitter;
        }

    }

}
