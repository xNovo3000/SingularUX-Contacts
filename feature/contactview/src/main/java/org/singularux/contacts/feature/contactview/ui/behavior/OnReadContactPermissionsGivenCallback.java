package org.singularux.contacts.feature.contactview.ui.behavior;

import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactview.R;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;
import org.singularux.contacts.feature.contactview.ui.EmailAddressListAdapter;
import org.singularux.contacts.feature.contactview.ui.PhoneNumberListAdapter;
import org.singularux.contacts.feature.contactview.ui.util.ContactPhotoCache;

import java.util.Map;
import java.util.Optional;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class OnReadContactPermissionsGivenCallback
        implements ActivityResultCallback<Map<String, Boolean>> {

    private final ComponentActivity activity;
    private final ContactViewViewModel contactViewViewModel;
    private final ContactPhotoCache contactPhotoCache;
    private final Scheduler ioScheduler;

    private final EmailAddressListAdapter emailAddressListAdapter;
    private final PhoneNumberListAdapter phoneNumberListAdapter;

    private final MaterialToolbar contactViewToolbar;
    private final ShapeableImageView avatarImage;
    private final MaterialTextView avatarText, displayName, headerPhone, headerEmail;

    private @Nullable Disposable avatarImageLoad = null;

    @Override
    public void onActivityResult(@NonNull Map<String, Boolean> result) {
        boolean hasReadContactPermissions = result.values().stream()
                .allMatch(Boolean::booleanValue);
        if (hasReadContactPermissions) {
            // Observe and update all views
            contactViewViewModel.getItemContactLiveData().observe(activity, maybeItemContact -> {
                // Close activity if null
                if (maybeItemContact.isEmpty()) {
                    activity.finish();
                    return;
                }
                val itemContact = maybeItemContact.get();
                // Update menu bar - delete item
                val deleteItem = contactViewToolbar.getMenu()
                        .findItem(R.id.contact_view_toolbar_delete);
                deleteItem.setVisible(!itemContact.isUserProfile());
                deleteItem.setEnabled(!itemContact.isUserProfile());
                // Update menu bar - favorite item
                val favoriteAddItem = contactViewToolbar.getMenu()
                        .findItem(R.id.contact_view_toolbar_favorite_add);
                val favoriteRemoveItem = contactViewToolbar.getMenu()
                        .findItem(R.id.contact_view_toolbar_favorite_remove);
                if (itemContact.isStarred()) {
                    favoriteAddItem.setVisible(false);
                    favoriteAddItem.setEnabled(false);
                    favoriteRemoveItem.setVisible(true);
                    favoriteRemoveItem.setEnabled(true);
                } else {
                    favoriteAddItem.setVisible(true);
                    favoriteAddItem.setEnabled(true);
                    favoriteRemoveItem.setVisible(false);
                    favoriteRemoveItem.setEnabled(false);
                }
                // Update avatar image
                if (avatarImageLoad != null) {
                    avatarImageLoad.dispose();
                }
                if (itemContact.getPhotoPath() != null) {
                    avatarImageLoad = Maybe
                            .fromCallable(() -> Optional.ofNullable(contactPhotoCache.get(itemContact.getPhotoPath())))
                            .subscribeOn(ioScheduler)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(maybeBitmap -> maybeBitmap.ifPresent(avatarImage::setImageBitmap));
                } else {
                    avatarImage.setImageResource(0);
                }
                // Update avatar text
                char firstCharacter = 0x00B7;
                if (!itemContact.getDisplayName().isBlank()) {
                    firstCharacter = itemContact.getDisplayName().charAt(0);
                }
                avatarText.setText(String.valueOf(firstCharacter));
                // Update display name
                displayName.setText(itemContact.getDisplayName());
                // Check if phone and email headers must be visible or not
                headerPhone.setVisibility(itemContact.getPhoneNumbersList().isEmpty() ?
                        View.GONE : View.VISIBLE);
                headerEmail.setVisibility(itemContact.getEmailAddressList().isEmpty() ?
                        View.GONE : View.VISIBLE);
                // Update phone and email adapters
                emailAddressListAdapter.submitList(itemContact.getEmailAddressList());
                phoneNumberListAdapter.submitList(itemContact.getPhoneNumbersList());
            });
        }
    }

}
