package org.singularux.contacts.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.ContactBriefEntity;
import org.singularux.contacts.ui.ComponentContactData;
import org.singularux.contacts.ui.ComponentData;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ContactBriefEntityTransformations {

    private ContactBriefEntityTransformations() {}

    public static final class WithHeaders
            implements Function<List<ContactBriefEntity>, List<ComponentData>> {

        @Override
        public @NonNull List<ComponentData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntities) {
            // TODO: Implement
            return Collections.emptyList();
        }

    }

    public static final class Standard
            implements Function<ContactBriefEntity, ComponentData> {

        @Override
        public @NonNull ComponentData apply(@NonNull ContactBriefEntity contactBriefEntity) {
            return new ComponentContactData(contactBriefEntity.getLookupKey(),
                    contactBriefEntity.getDisplayName(), contactBriefEntity.getThumbnailPath());
        }

    }

}
