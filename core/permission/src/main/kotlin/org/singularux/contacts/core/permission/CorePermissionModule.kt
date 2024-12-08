package org.singularux.contacts.core.permission

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object CorePermissionModule {

    @Provides
    @ActivityRetainedScoped
    fun getPermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManagerAndroid(context = context)
    }

}