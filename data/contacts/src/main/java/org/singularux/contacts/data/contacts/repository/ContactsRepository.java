package org.singularux.contacts.data.contacts.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;

import java.util.List;

public interface ContactsRepository {
    @NonNull List<ContactBriefEntity> getAll();
    @NonNull List<ContactBriefEntity> getByDisplayNameLike(@NonNull String query);
    @Nullable ContactBriefEntity getByLookupKey(String lookupKey);
}
