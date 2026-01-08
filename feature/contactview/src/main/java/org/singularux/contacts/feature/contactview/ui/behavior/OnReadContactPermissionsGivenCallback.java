package org.singularux.contacts.feature.contactview.ui.behavior;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;
import org.singularux.contacts.feature.contactview.ui.EmailAddressListAdapter;
import org.singularux.contacts.feature.contactview.ui.PhoneNumberListAdapter;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnReadContactPermissionsGivenCallback
        implements ActivityResultCallback<Map<String, Boolean>> {

    private final LifecycleOwner lifecycleOwner;
    private final ContactViewViewModel contactViewViewModel;

    private final EmailAddressListAdapter emailAddressListAdapter;
    private final PhoneNumberListAdapter phoneNumberListAdapter;

    private final ShapeableImageView avatarImage;
    private final MaterialTextView avatarText, displayName;

    @Override
    public void onActivityResult(@NonNull Map<String, Boolean> result) {
        boolean hasReadContactPermissions = result.values().stream()
                .allMatch(Boolean::booleanValue);
        if (hasReadContactPermissions) {
            // Observe and update all views
            contactViewViewModel.getItemContactLiveData().observe(lifecycleOwner, itemContact -> {
                // TODO: Update menu bar
                // TODO: Update avatar image
                // Update avatar text
                char firstCharacter = 0x00B7;
                if (!itemContact.getDisplayName().isBlank()) {
                    firstCharacter = itemContact.getDisplayName().charAt(0);
                }
                avatarText.setText(String.valueOf(firstCharacter));
                // Update display name
                displayName.setText(itemContact.getDisplayName());
                // Update phone and email adapters
                emailAddressListAdapter.submitList(itemContact.getEmailAddressList());
                phoneNumberListAdapter.submitList(itemContact.getPhoneNumbersList());
            });
        }
    }

}
