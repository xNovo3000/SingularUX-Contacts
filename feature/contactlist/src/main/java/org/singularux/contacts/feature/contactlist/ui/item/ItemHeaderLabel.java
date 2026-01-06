package org.singularux.contacts.feature.contactlist.ui.item;

import androidx.annotation.StringRes;

import org.singularux.contacts.feature.contactlist.R;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemHeaderLabel {
    SYMBOLS(R.string.item_header_symbols);
    public final @StringRes Integer labelRes;
}
