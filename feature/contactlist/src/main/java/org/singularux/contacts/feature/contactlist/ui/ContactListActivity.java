package org.singularux.contacts.feature.contactlist.ui;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.lifecycle.ViewModelProvider;

import org.singularux.contacts.feature.contactlist.databinding.ActivityContactListBinding;
import org.singularux.contacts.feature.contactlist.presentation.ContactListViewModel;
import org.singularux.contacts.feature.contactlist.ui.behavior.ContactListFabHideOnScrollListener;
import org.singularux.contacts.feature.contactlist.ui.behavior.ContactListObserveWhenGivenPermissionCallback;
import org.singularux.contacts.feature.contactlist.ui.behavior.OnCloseMenuItemClickListener;
import org.singularux.contacts.feature.contactlist.ui.behavior.OnSelectionMenuItemClickListener;
import org.singularux.contacts.feature.contactlist.ui.inset.ContactListFabInsetListener;
import org.singularux.contacts.feature.contactlist.ui.inset.ContactListRecyclerViewInsetListener;
import org.singularux.contacts.feature.contactlist.ui.inset.ContactListSearchBarInsetListener;
import org.singularux.contacts.feature.contactlist.ui.inset.ContactListSearchRecyclerViewInsetListener;
import org.singularux.contacts.feature.contactlist.ui.behavior.ContactListSearchTextWatcher;
import org.singularux.contacts.feature.contactlist.ui.inset.ContactListSelectionToolbarInsetListener;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lombok.val;

@AndroidEntryPoint
public class ContactListActivity extends ComponentActivity {

    public @Inject ContactListRecyclerViewAdapter contactListRecyclerViewAdapter;
    public @Inject ContactListSearchRecyclerViewAdapter contactListSearchRecyclerViewAdapter;

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
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListSelectionToolbar,
                new ContactListSelectionToolbarInsetListener());
        // Install behavior listeners
        binding.contactListRecyclerview.addOnScrollListener(
                new ContactListFabHideOnScrollListener(binding.contactListFab));
        binding.contactListSelectionToolbar.setNavigationOnClickListener(
                new OnCloseMenuItemClickListener());
        binding.contactListSelectionToolbar.setOnMenuItemClickListener(
                new OnSelectionMenuItemClickListener());

        // Set adapters
        binding.contactListRecyclerview.setAdapter(contactListRecyclerViewAdapter);
        binding.contactListSearchRecyclerview.setAdapter(contactListSearchRecyclerViewAdapter);
        // Get ViewModel
        val viewModel = new ViewModelProvider(this)
                .get(ContactListViewModel.class);
        // Request permission to listen for contacts
        val requestReadContactsPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ContactListObserveWhenGivenPermissionCallback(this, viewModel,
                        contactListRecyclerViewAdapter, contactListSearchRecyclerViewAdapter));
        requestReadContactsPermissionLauncher.launch(viewModel.getReadContactsPermissions());

        // Install text listeners
        binding.contactListSearchView.getEditText().addTextChangedListener(
                new ContactListSearchTextWatcher(viewModel));

    }

}
