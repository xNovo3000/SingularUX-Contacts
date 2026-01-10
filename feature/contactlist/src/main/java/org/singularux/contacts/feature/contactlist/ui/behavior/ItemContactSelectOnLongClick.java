package org.singularux.contacts.feature.contactlist.ui.behavior;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemContactSelectOnLongClick implements View.OnLongClickListener {

    private final long id;
    private final SelectionTracker<Long> selectionTracker;

    @Override
    public boolean onLongClick(@NonNull View v) {
        selectionTracker.select(id);
        return true;
    }

}
