package org.singularux.contacts.feature.contactview.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.core.threading.IOScheduler;
import org.singularux.contacts.data.contacts.repository.ContactsRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;

public class SetStarredUseCase {

    private final ContactsRepository contactsRepository;
    private final Scheduler ioScheduler;

    private @Nullable Disposable currentAction = null;

    @Inject
    public SetStarredUseCase(ContactsRepository contactsRepository,
                             @IOScheduler Scheduler ioScheduler) {
        this.contactsRepository = contactsRepository;
        this.ioScheduler = ioScheduler;
    }

    public void set(@NonNull String lookupKey, boolean starred) {
        // Stop current action if still performing
        if (currentAction != null) {
            currentAction.dispose();
        }
        // Start new action
        currentAction = Completable
                .fromCallable(() -> contactsRepository.setStarredByLookupKey(lookupKey, starred))
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
