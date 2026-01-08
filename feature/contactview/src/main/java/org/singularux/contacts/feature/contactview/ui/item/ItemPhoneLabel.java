package org.singularux.contacts.feature.contactview.ui.item;

import androidx.annotation.StringRes;

import org.singularux.contacts.feature.contactview.R;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemPhoneLabel {
    HOME(R.string.item_phone_label_home),
    MOBILE(R.string.item_phone_label_mobile),
    WORK(R.string.item_phone_label_work);
    public final @StringRes Integer labelRes;
}
