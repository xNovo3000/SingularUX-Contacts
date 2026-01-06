package org.singularux.contacts.feature.contactview.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import org.singularux.contacts.feature.contactview.databinding.ActivityContactViewBinding;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModel;
import org.singularux.contacts.feature.contactview.presentation.ContactViewViewModelFactory;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModelExtensions;
import lombok.val;

@AndroidEntryPoint
public class ContactViewActivity extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Start activity always in edge-to-edge mode
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        // Set view using viewbinding
        val binding = ActivityContactViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        val defaultFactory = getDefaultViewModelProviderFactory();
        val creationExtras = HiltViewModelExtensions.withCreationCallback(
                getDefaultViewModelCreationExtras(),
                (ContactViewViewModelFactory factory) -> factory.create(lookupKey));
        val viewModel = new ViewModelProvider(getViewModelStore(), defaultFactory, creationExtras)
                .get(ContactViewViewModel.class);

        viewModel.getContactEntityLiveData().observe(this, data -> Log.d("Final", data.toString()));
    }

}
