package org.singularux.contacts.feature.contactlist.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemData;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.functions.Function;

class TransformationsCollection {

    private TransformationsCollection() {}

    public static final class ContactList
            implements Function<List<ContactBriefEntity>, List<ItemData>> {

        @Override
        public @NonNull List<ItemData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntityList) {
            // TODO: Implement headers
            return contactBriefEntityList.stream()
                    .map(new TransformationsEntity.IContactBriefEntity())
                    .collect(Collectors.toList());
        }

    }

    public static final class SearchContactList
            implements Function<List<ContactBriefEntity>, List<ItemContactData>> {

        @Override
        public @NonNull List<ItemContactData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntityList) {
            return contactBriefEntityList.stream()
                    .map(new TransformationsEntity.IContactBriefEntity())
                    .collect(Collectors.toList());
        }

    }

}
