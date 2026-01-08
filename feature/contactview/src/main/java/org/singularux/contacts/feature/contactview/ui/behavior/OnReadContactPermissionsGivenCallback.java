package org.singularux.contacts.feature.contactview.ui.behavior;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnReadContactPermissionsGivenCallback
        implements ActivityResultCallback<Map<String, Boolean>> {

    private final LifecycleOwner lifecycleOwner;
    private final ContactViewViewModel contactViewViewModel;

    private final ShapeableImageView avatarImage;
    private final MaterialTextView avatarText, displayName;

    @Override
    public void onActivityResult(@NonNull Map<String, Boolean> result) {
        boolean hasReadContactsPermissions = result.values().stream()
                .allMatch(Boolean::booleanValue);
        if (hasReadContactsPermissions) {
            // TODO: Observe and update data
            contactViewViewModel.getItemContactLiveData().observe(lifecycleOwner, itemContact -> {

            });
        }
    }

}
