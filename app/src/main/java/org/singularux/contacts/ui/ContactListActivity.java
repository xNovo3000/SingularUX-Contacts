package org.singularux.contacts.ui;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;

import org.singularux.contacts.databinding.ActivityContactListBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lombok.val;

@AndroidEntryPoint
public class ContactListActivity extends ComponentActivity {

    @Inject public ContactListRecyclerViewAdapter contactListRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Start activity with splash screen and edge-to-edge support
        SplashScreen.installSplashScreen(this);
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        // Set content view using view binding
        val binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Inset listeners
        ViewGroupCompat.installCompatInsetsDispatch(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListRecyclerview,
                new ContactListRecyclerViewInsetListener());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListSearchBar,
                new ContactListSearchBarInsetListener());
        ViewCompat.setOnApplyWindowInsetsListener(binding.contactListFab,
                new ContactListFabInsetListener());
        // Set adapters
        binding.contactListRecyclerview.setAdapter(contactListRecyclerViewAdapter);
    }

}
