package org.singularux.contacts.data.contacts.repository;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;

import java.util.List;

public interface ContactsRepository {
    List<ContactBriefEntity> getAll();
    List<ContactBriefEntity> getByDisplayNameLike(@NonNull String query);
}
