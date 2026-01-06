package org.singularux.contacts.data.contacts.entity;

import androidx.annotation.NonNull;

import java.util.List;

import lombok.Value;

@Value
public class ContactEntity {
    @NonNull ContactBriefEntity contactBriefEntity;
    @NonNull List<PhoneNumberEntity> phoneNumberList;
    @NonNull List<EmailAddressEntity> emailAddressList;
}
