package org.singularux.contacts.feature.contactlist.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.feature.contactlist.ui.ComponentContactData;

import java.util.function.Function;

class ContactBriefEntityTransformation
        implements Function<ContactBriefEntity, ComponentContactData> {

    @Override
    public @NonNull ComponentContactData apply(@NonNull ContactBriefEntity contactBriefEntity) {
        return new ComponentContactData(contactBriefEntity.getLookupKey(),
                contactBriefEntity.getDisplayName(), contactBriefEntity.getThumbnailPath());
    }

}
