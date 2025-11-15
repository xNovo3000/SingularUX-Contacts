package org.singularux.contacts.core.permission

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CorePermissionModuleBindings {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindsContactsPermissionManager(
        implementation: ContactsPermissionManagerAndroid
    ): ContactsPermissionManager

}