package org.singularux.contacts.ui;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.lifecycle.ViewModelProvider;

import org.singularux.contacts.databinding.ActivityContactListBinding;
import org.singularux.contacts.presentation.ContactListViewModel;

import java.util.Arrays;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lombok.val;

@AndroidEntryPoint
public class ContactListActivity extends ComponentActivity
        implements ActivityResultCallback<Map<String, Boolean>> {

    @Inject public ContactListRecyclerViewAdapter contactListRecyclerViewAdapter;
    @Inject public ContactListSearchRecyclerViewAdapter contactListSearchRecyclerViewAdapter;

    private ContactListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Start activity with splash screen and edge-to-edge support
        SplashScreen.installSplashScreen(this);
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        // Set content view using view binding
        val binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Install inset listeners
        ViewGroupCompat.installCompatInsetsDispatch(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListRecyclerview,
                new ContactListRecyclerViewInsetListener());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListSearchBar,
                new ContactListSearchBarInsetListener());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListFab,
                new ContactListFabInsetListener());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListSearchRecyclerview,
                new ContactListSearchRecyclerViewInsetListener());
        // Install behavior listeners
        binding.contactListRecyclerview.addOnScrollListener(
                new ContactListFabHideOnScrollListener(binding.contactListFab));
        // Set adapters
        binding.contactListRecyclerview.setAdapter(contactListRecyclerViewAdapter);
        binding.contactListSearchRecyclerview.setAdapter(contactListSearchRecyclerViewAdapter);
        // Get ViewModel
        viewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        // Request permission to listen for contacts
        val requestReadContactsPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), this);
        requestReadContactsPermissionLauncher.launch(viewModel.getReadContactsPermissions());
        // Install text listeners
        binding.contactListSearchView.getEditText().addTextChangedListener(
                new ContactListSearchTextWatcher(viewModel));
    }

    @Override
    public void onActivityResult(@NonNull Map<String, Boolean> result) {
        // Listen read contacts permission check
        boolean hasReadContactsPermissions = Arrays.stream(viewModel.getReadContactsPermissions())
                .allMatch(permission -> result.getOrDefault(permission, false));
        if (hasReadContactsPermissions) {
            viewModel.getContactListLiveData().observe(this, componentDataList ->
                    contactListRecyclerViewAdapter.submitList(componentDataList));
            viewModel.getSearchContactListLiveData().observe(this, componentContactDataList ->
                    contactListSearchRecyclerViewAdapter.submitList(componentContactDataList));
        }
    }

}
