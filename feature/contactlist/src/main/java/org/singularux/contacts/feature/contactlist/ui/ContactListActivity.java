package org.singularux.contacts.feature.contactlist.ui;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;

import org.singularux.contacts.core.threading.BackgroundExecutorService;
import org.singularux.contacts.core.threading.IOScheduler;
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
import org.singularux.contacts.feature.contactlist.ui.util.ContactThumbnailCache;
import org.singularux.contacts.feature.contactlist.ui.util.OnlyContactSelectionPredicate;
import org.singularux.contacts.feature.contactlist.ui.util.StandardRecyclerViewItemDetailsLookup;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Scheduler;
import lombok.val;

@AndroidEntryPoint
public class ContactListActivity extends ComponentActivity {

    public @Inject ContactListSearchRecyclerViewAdapter contactListSearchRecyclerViewAdapter;

    public @Inject @BackgroundExecutorService ExecutorService backgroundExecutorService;
    public @Inject @IOScheduler Scheduler ioScheduler;
    public @Inject ContactThumbnailCache contactThumbnailCache;

    public ActivityContactListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Start activity with splash screen and edge-to-edge support
        SplashScreen.installSplashScreen(this);
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        // Set content view using view binding
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
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

        // Create standard RecyclerView adapter
        SelectionTracker<Long> selectionTracker =
                new SelectionTracker.Builder<>("selection",
                        binding.contactListRecyclerview,
                        new StableIdKeyProvider(binding.contactListRecyclerview),
                        new StandardRecyclerViewItemDetailsLookup(binding.contactListRecyclerview),
                        StorageStrategy.createLongStorage())
                        .withSelectionPredicate(new OnlyContactSelectionPredicate())
                        .build();
        val contactListRecyclerViewAdapter = new ContactListRecyclerViewAdapter(
                backgroundExecutorService, ioScheduler, contactThumbnailCache, selectionTracker);

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

    public static final String RV_STATE_KEY = "ContactListRecyclerViewAdapterState";
    public static final String SRV_STATE_KEY = "ContactListSearchRecyclerViewAdapterState";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Save standard RecyclerView state
        assert binding.contactListRecyclerview.getLayoutManager() != null;
        val rvState = binding.contactListRecyclerview.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RV_STATE_KEY, rvState);
        // Save search RecyclerView state
        assert binding.contactListSearchRecyclerview.getLayoutManager() != null;
        val srvState = binding.contactListSearchRecyclerview.getLayoutManager()
                .onSaveInstanceState();
        outState.putParcelable(SRV_STATE_KEY, srvState);
        // Always call last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Always call first
        super.onRestoreInstanceState(savedInstanceState);
        // Restore standard RecyclerView state
        val rvState = savedInstanceState.getParcelable(RV_STATE_KEY);
        if (rvState != null) {
            assert binding.contactListRecyclerview.getLayoutManager() != null;
            binding.contactListRecyclerview.getLayoutManager().onRestoreInstanceState(rvState);
        }
        // Restore search RecyclerView state
        val srvState = savedInstanceState.getParcelable(RV_STATE_KEY);
        if (srvState != null) {
            assert binding.contactListSearchRecyclerview.getLayoutManager() != null;
            binding.contactListSearchRecyclerview.getLayoutManager().onRestoreInstanceState(srvState);
        }
    }

}
