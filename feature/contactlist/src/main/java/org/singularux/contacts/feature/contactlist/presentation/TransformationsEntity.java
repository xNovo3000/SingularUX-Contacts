package org.singularux.contacts.feature.contactlist.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemHeaderData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemHeaderLabel;

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

    public static class IContactBriefEntityHeaderGrouping
            implements Function<ContactBriefEntity, ItemHeaderData> {

        @Override
        public @NonNull ItemHeaderData apply(@NonNull ContactBriefEntity contactBriefEntity) {
            // Starred contacts
            if (contactBriefEntity.isStarred()) {
                return new ItemHeaderData(ItemHeaderLabel.STARRED, null);
            }
            // Get first character
            char firstCharacter = 0x00B7;
            if (!contactBriefEntity.getDisplayName().isBlank()) {
                firstCharacter = contactBriefEntity.getDisplayName().charAt(0);
            }
            // Check if letter or non-letter
            if (Character.isLetter(firstCharacter)) {
                return new ItemHeaderData(null, String.valueOf(firstCharacter));
            } else {
                return new ItemHeaderData(ItemHeaderLabel.SYMBOLS, null);
            }
        }

    }

}
