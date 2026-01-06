package org.singularux.contacts.feature.contactlist.presentation;

import androidx.annotation.NonNull;

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemData;
import org.singularux.contacts.feature.contactlist.ui.item.ItemHeaderData;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.rxjava3.functions.Function;
import lombok.val;

class TransformationsCollection {

    private TransformationsCollection() {}

    public static final class ContactList
            implements Function<List<ContactBriefEntity>, List<ItemData>> {

        @Override
        public @NonNull List<ItemData> apply(
                @NonNull List<ContactBriefEntity> contactBriefEntityList) {
            return contactBriefEntityList.stream()
                    // Group by header
                    .collect(Collectors.groupingBy(
                            new TransformationsEntity.IContactBriefEntityHeaderGrouping(),
                            () -> {
                                val comparator = Comparator
                                        .comparing(ItemHeaderData::getStandardLabel, Comparator.nullsLast(Comparator.comparingInt(Enum::ordinal)))
                                        .thenComparing(ItemHeaderData::getLabel, Comparator.nullsLast(Comparator.naturalOrder()));
                                return new TreeMap<>(comparator);
                            },
                            Collectors.toList()))
                    .entrySet().stream()
                    // Flatten headers and contact item
                    .flatMap(entry -> Stream.concat(Stream.of(entry.getKey()),
                            entry.getValue().stream().map(new TransformationsEntity.IContactBriefEntity())))
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
