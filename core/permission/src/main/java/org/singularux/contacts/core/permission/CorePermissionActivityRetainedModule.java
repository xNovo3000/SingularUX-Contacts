package org.singularux.contacts.core.permission;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public class CorePermissionActivityRetainedModule {

    @Provides
    @ActivityRetainedScoped
    public ContactsPermissionManager providesContactsPermissionManager(
            @ApplicationContext Context context
    ) {
        return new ContactsPermissionManagerAndroid(context);
    }

}
