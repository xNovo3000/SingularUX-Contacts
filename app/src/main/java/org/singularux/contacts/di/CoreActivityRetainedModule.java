package org.singularux.contacts.di;

import android.content.Context;

import org.singularux.contacts.core.ContactsPermissionManager;
import org.singularux.contacts.core.ContactsPermissionManagerAndroid;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class CoreActivityRetainedModule {

    @Provides
    @ActivityRetainedScoped
    public ContactsPermissionManager providesContactsPermissionManager(
            @ApplicationContext Context context) {
        return new ContactsPermissionManagerAndroid(context);
    }

}
