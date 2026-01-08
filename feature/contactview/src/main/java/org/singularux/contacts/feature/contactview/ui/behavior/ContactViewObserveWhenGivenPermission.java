package org.singularux.contacts.feature.contactview.ui.behavior;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;

import java.util.Map;

public class ContactViewObserveWhenGivenPermission
        implements ActivityResultCallback<Map<String, Boolean>> {

    @Override
    public void onActivityResult(@NonNull Map<String, Boolean> result) {
        boolean hasReadContactsPermissions = result.values().stream()
                .allMatch(Boolean::booleanValue);
        if (hasReadContactsPermissions) {
            // TODO: Observe and update data
        }
    }

}
