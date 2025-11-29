package org.singularux.contacts.feature.contactlist.presentation

import org.singularux.contacts.data.contacts.entity.ContactBriefEntity
import org.singularux.contacts.feature.contactlist.ui.item.ContactBriefItemData
import org.singularux.contacts.feature.contactlist.ui.item.ContactHeaderItemData

fun fromContactBriefEntityListToUiList(
    contactBriefEntityList: List<ContactBriefEntity>
): List<Pair<ContactHeaderItemData, List<ContactBriefItemData>>> {
    return contactBriefEntityList
        .groupBy {
            when {
                it.isUserProfile -> ContactHeaderItemData.UserProfile
                it.isStarred -> ContactHeaderItemData.Starred
                it.displayName.firstOrNull()?.isLetter() == true ->
                    ContactHeaderItemData.Initial(it.displayName.first().uppercaseChar())
                else -> ContactHeaderItemData.Symbols
            }
        }
        .mapValues { entry ->
            entry.value.map {
                ContactBriefItemData(
                    lookupKey = it.lookupKey,
                    displayName = it.displayName,
                    photoThumbnailUri = it.photoThumbnailUri
                )
            }
        }
        .toSortedMap(
            comparator = compareBy {
                when (it) {
                    ContactHeaderItemData.UserProfile -> -2
                    ContactHeaderItemData.Starred -> -1
                    is ContactHeaderItemData.Initial -> it.letter.code
                    ContactHeaderItemData.Symbols -> Int.MAX_VALUE
                }
            }
        )
        .toList()
}