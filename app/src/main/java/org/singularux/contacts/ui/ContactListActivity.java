package org.singularux.contacts.ui;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.WindowCompat;

import org.singularux.contacts.databinding.ActivityContactListBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContactListActivity extends ComponentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Start activity with splash screen and edge-to-edge support
        SplashScreen.installSplashScreen(this);
        WindowCompat.enableEdgeToEdge(getWindow());
        super.onCreate(savedInstanceState);
        // Set content view using view binding
        ActivityContactListBinding binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}
