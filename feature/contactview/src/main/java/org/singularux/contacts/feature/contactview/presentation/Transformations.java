package org.singularux.contacts.feature.contactview.presentation;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.singularux.contacts.data.contacts.entity.ContactEntity;
import org.singularux.contacts.data.contacts.entity.EmailAddressEntity;
import org.singularux.contacts.data.contacts.entity.PhoneNumberEntity;
import org.singularux.contacts.feature.contactview.ui.item.ItemContact;
import org.singularux.contacts.feature.contactview.ui.item.ItemEmailData;
import org.singularux.contacts.feature.contactview.ui.item.ItemEmailLabel;
import org.singularux.contacts.feature.contactview.ui.item.ItemPhoneData;
import org.singularux.contacts.feature.contactview.ui.item.ItemPhoneLabel;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.val;

public class Transformations {

    private Transformations() {}

    public static class IItemContact implements Function<ContactEntity, ItemContact> {

        @Override
        public @Nullable ItemContact apply(@Nullable ContactEntity contactEntity) {
            assert contactEntity != null;
            // Retrieve photo path
            Uri photoPath = contactEntity.getPhotoEntity().getPhotoPath();
            if (photoPath == null) {
                photoPath = contactEntity.getContactBriefEntity().getThumbnailPath();
            }
            // Create phone and email results
            val phoneNumberList = contactEntity.getPhoneNumberList().stream()
                    .map(new IItemPhoneData())
                    .collect(Collectors.toList());
            val emailAddressList = contactEntity.getEmailAddressList().stream()
                    .map(new IItemEmailData())
                    .collect(Collectors.toList());
            // Create item data
            return new ItemContact(contactEntity.getContactBriefEntity().getDisplayName(),
                    photoPath, contactEntity.getContactBriefEntity().isStarred(),
                    false, emailAddressList, phoneNumberList);
        }

    }

    public static class IItemEmailData implements Function<EmailAddressEntity, ItemEmailData> {

        @Override
        public @NonNull ItemEmailData apply(@NonNull EmailAddressEntity emailAddressEntity) {
            // Get label
            ItemEmailLabel label;
            switch (emailAddressEntity.getLabel()) {
                case CUSTOM:
                    label = null;
                    break;
                case HOME:
                    label = ItemEmailLabel.HOME;
                    break;
                case MOBILE:
                    label = ItemEmailLabel.MOBILE;
                    break;
                case WORK:
                    label = ItemEmailLabel.WORK;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid email label: " + emailAddressEntity.getLabel());
            }
            // Create item data
            return new ItemEmailData(emailAddressEntity.getAddress(), label,
                    emailAddressEntity.getCustomLabel());
        }

    }

    public static class IItemPhoneData implements Function<PhoneNumberEntity, ItemPhoneData> {

        @Override
        public @NonNull ItemPhoneData apply(@NonNull PhoneNumberEntity phoneNumberEntity) {
            // Get label
            ItemPhoneLabel label;
            switch (phoneNumberEntity.getLabel()) {
                case CUSTOM:
                    label = null;
                    break;
                case HOME:
                    label = ItemPhoneLabel.HOME;
                    break;
                case MOBILE:
                    label = ItemPhoneLabel.MOBILE;
                    break;
                case WORK:
                    label = ItemPhoneLabel.WORK;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid phone label: " + phoneNumberEntity.getLabel());
            }
            // Create item data
            return new ItemPhoneData(phoneNumberEntity.getNumber(), label,
                    phoneNumberEntity.getCustomLabel());
        }

    }

}
