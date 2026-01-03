package org.singularux.contacts.data;

import androidx.annotation.NonNull;

import java.util.List;

public interface ContactRepository {
    List<ContactBriefEntity> getAll();
    List<ContactBriefEntity> getByDisplayNameLike(@NonNull String query);
}
