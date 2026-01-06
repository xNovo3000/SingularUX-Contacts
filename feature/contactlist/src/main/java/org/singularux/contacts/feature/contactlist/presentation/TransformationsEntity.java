package org.singularux.contacts.feature.contactlist.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;

import java.util.function.Function;

class TransformationsEntity {

    private TransformationsEntity() {}

    public static class IContactBriefEntity
            implements Function<ContactBriefEntity, ItemContactData> {

        @Override
        public @NonNull ItemContactData apply(@NonNull ContactBriefEntity contactBriefEntity) {
            return new ItemContactData(contactBriefEntity.getLookupKey(),
                    contactBriefEntity.getDisplayName(), contactBriefEntity.getThumbnailPath());
        }

    }

}
