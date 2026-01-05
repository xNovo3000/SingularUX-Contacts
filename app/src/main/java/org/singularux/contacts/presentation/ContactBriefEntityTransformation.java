package org.singularux.contacts.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.ContactBriefEntity;
import org.singularux.contacts.ui.ComponentContactData;
import org.singularux.contacts.ui.ComponentData;

import java.util.function.Function;

class ContactBriefEntityTransformation
        implements Function<ContactBriefEntity, ComponentContactData> {

    @Override
    public @NonNull ComponentContactData apply(@NonNull ContactBriefEntity contactBriefEntity) {
        return new ComponentContactData(contactBriefEntity.getLookupKey(),
                contactBriefEntity.getDisplayName(), contactBriefEntity.getThumbnailPath());
    }

}
