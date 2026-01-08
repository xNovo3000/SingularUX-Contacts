package org.singularux.contacts.feature.contactview.ui.item;

import androidx.annotation.StringRes;

import org.singularux.contacts.feature.contactview.R;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemEmailLabel {
    HOME(R.string.item_email_label_home),
    MOBILE(R.string.item_email_label_mobile),
    WORK(R.string.item_email_label_work);
    public final @StringRes Integer labelRes;
}
