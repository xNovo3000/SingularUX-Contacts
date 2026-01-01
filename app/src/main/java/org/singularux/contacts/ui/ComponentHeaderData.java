package org.singularux.contacts.ui;

import androidx.annotation.StringRes;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = false)
@Value
public class ComponentHeaderData extends ComponentData {

    @StringRes Integer labelRes;
    String label;

    @Override
    public int getId() {
        return hashCode();
    }

}
