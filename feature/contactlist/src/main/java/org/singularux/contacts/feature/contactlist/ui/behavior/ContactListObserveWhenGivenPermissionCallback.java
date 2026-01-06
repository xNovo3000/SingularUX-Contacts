package org.singularux.contacts.feature.contactlist.ui.behavior;

import androidx.activity.result.ActivityResultCallback;
import androidx.lifecycle.LifecycleOwner;

import org.singularux.contacts.feature.contactlist.presentation.ContactListViewModel;
import org.singularux.contacts.feature.contactlist.ui.ContactListRecyclerViewAdapter;
import org.singularux.contacts.feature.contactlist.ui.ContactListSearchRecyclerViewAdapter;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContactListObserveWhenGivenPermissionCallback
        implements ActivityResultCallback<Map<String, Boolean>> {

    private final LifecycleOwner lifecycleOwner;
    private final ContactListViewModel viewModel;
    private final ContactListRecyclerViewAdapter contactListRecyclerViewAdapter;
    private final ContactListSearchRecyclerViewAdapter contactListSearchRecyclerViewAdapter;

    @Override
    public void onActivityResult(Map<String, Boolean> result) {
        boolean hasReadContactsPermissions = result.values().stream()
                .allMatch(Boolean::booleanValue);
        if (hasReadContactsPermissions) {
            viewModel.getContactListLiveData().observe(lifecycleOwner,
                    contactListRecyclerViewAdapter::submitList);
            viewModel.getSearchContactListLiveData().observe(lifecycleOwner,
                    contactListSearchRecyclerViewAdapter::submitList);
        }
    }

}
