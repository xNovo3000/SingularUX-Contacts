package org.singularux.contacts.feature.contactlist.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.feature.contactlist.ui.ComponentContactData;
import org.singularux.contacts.feature.contactlist.ui.ComponentData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.functions.Function;

public class ContactListTransformations {

    private ContactListTransformations() {}

    public static final class WithHeaders
            implements Function<List<ContactBriefEntity>, List<ComponentData>> {

        @Override
        public @NonNull List<ComponentData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntityList) {
            // TODO: Implement
            return Collections.emptyList();
        }

    }

    public static final class Standard
            implements Function<List<ContactBriefEntity>, List<ComponentData>> {

        @Override
        public @NonNull List<ComponentData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntityList) {
            return contactBriefEntityList.stream()
                    .map(new ContactBriefEntityTransformation())
                    .collect(Collectors.toList());
        }

    }

    public static final class Search
            implements Function<List<ContactBriefEntity>, List<ComponentContactData>> {

        @Override
        public @NonNull List<ComponentContactData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntityList) {
            return contactBriefEntityList.stream()
                    .map(new ContactBriefEntityTransformation())
                    .collect(Collectors.toList());
        }

    }

}
