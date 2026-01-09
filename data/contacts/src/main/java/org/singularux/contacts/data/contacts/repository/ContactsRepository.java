package org.singularux.contacts.data.contacts.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.data.contacts.entity.ContactEntity;

import java.util.List;

public interface ContactsRepository {
    @NonNull List<ContactBriefEntity> getAll();
    @NonNull List<ContactBriefEntity> getByDisplayNameLike(@NonNull String query);
    @Nullable ContactEntity getByLookupKey(@NonNull String lookupKey);
    boolean setStarred(@NonNull String lookupKey, boolean starred);
    boolean delete(@NonNull String lookupKey);
}
