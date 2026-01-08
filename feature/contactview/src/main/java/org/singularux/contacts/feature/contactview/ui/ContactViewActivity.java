package org.singularux.contacts.feature.contactview.ui;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import org.singularux.contacts.feature.contactview.databinding.ActivityContactViewBinding;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModelFactory;
import org.singularux.contacts.feature.contactview.ui.behavior.OnReadContactPermissionsGivenCallback;
import org.singularux.contacts.feature.contactview.ui.inset.ContactViewContentInsetListener;
import org.singularux.contacts.feature.contactview.ui.inset.ContactViewToolbarInsetListener;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModelExtensions;
import lombok.val;

@AndroidEntryPoint
public class ContactViewActivity extends ComponentActivity {

    public @Inject EmailAddressListAdapter emailAddressListAdapter;
    public @Inject PhoneNumberListAdapter phoneNumberListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Start activity always in edge-to-edge mode
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        // Set view using viewbinding
        val binding = ActivityContactViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Install inset listeners
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactViewToolbar,
                new ContactViewToolbarInsetListener());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactViewContent,
                new ContactViewContentInsetListener());
        // Install static behavior listeners
        binding.contactViewToolbar.setNavigationOnClickListener(v -> finish());

        // Set adapters
        binding.contactViewContentEmailAddresses.setAdapter(emailAddressListAdapter);
        binding.contactViewContentPhoneNumbers.setAdapter(phoneNumberListAdapter);

        // Get lookupKey, stop activity if does not exists
        String lookupKey;
        if (getIntent().getData() != null) {
            lookupKey = getIntent().getData().getLastPathSegment();
        } else {
            lookupKey = null;
        }
        if (lookupKey == null) {
            finish();
        }
        // Get ViewModel
        val providerFactory = getDefaultViewModelProviderFactory();
        val creationExtras = HiltViewModelExtensions.withCreationCallback(
                getDefaultViewModelCreationExtras(),
                (ContactViewViewModelFactory factory) -> factory.create(lookupKey));
        val viewModel = new ViewModelProvider(getViewModelStore(), providerFactory, creationExtras)
                .get(ContactViewViewModel.class);

        // Observe data
        val readContactPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new OnReadContactPermissionsGivenCallback(this, viewModel,
                        emailAddressListAdapter, phoneNumberListAdapter,
                        binding.contactViewContentAvatarImage,
                        binding.contactViewContentAvatarText,
                        binding.contactViewContentDisplayName));
        readContactPermissionLauncher.launch(viewModel.getReadContactPermissions());

    }

}
