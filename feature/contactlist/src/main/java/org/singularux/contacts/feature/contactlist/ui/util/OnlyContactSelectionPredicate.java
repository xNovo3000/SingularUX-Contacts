package org.singularux.contacts.feature.contactlist.ui.util;

import androidx.recyclerview.selection.SelectionTracker;

import org.jspecify.annotations.NonNull;
import org.singularux.contacts.feature.contactlist.ui.item.ItemContactData;

public class OnlyContactSelectionPredicate extends SelectionTracker.SelectionPredicate<Long> {

    @Override
    public boolean canSetStateForKey(@NonNull Long key, boolean nextState) {
        // Select only ItemContactData ids
        return (key & 0xFFFFFFFF00000000L) == ItemContactData.DATA_TYPE_ID;
    }

    @Override
    public boolean canSetStateAtPosition(int position, boolean nextState) {
        return true;
    }

    @Override
    public boolean canSelectMultiple() {
        return true;
    }

}
