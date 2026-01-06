package org.singularux.contacts.feature.contactlist.ui.item;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ItemHeaderData extends ItemData {

    public static final long DATA_TYPE_ID = 100L << 32;

    ItemHeaderLabel standardLabel;
    String label;

    @Override
    public long getId() {
        return DATA_TYPE_ID | (hashCode() & 0xFFFFFFFFL);
    }

}
