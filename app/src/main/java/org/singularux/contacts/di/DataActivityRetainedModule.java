package org.singularux.contacts.di;

import android.content.Context;

import org.singularux.contacts.core.ContactsPermissionManager;
import org.singularux.contacts.data.ContactsRepository;
import org.singularux.contacts.data.ContactsRepositoryAndroid;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class DataActivityRetainedModule {

    @Provides
    @ActivityRetainedScoped
    public ContactsRepository providesContactsRepository(
            @ApplicationContext Context context,
            ContactsPermissionManager contactsPermissionManager) {
        return new ContactsRepositoryAndroid(context, contactsPermissionManager);
    }

}
