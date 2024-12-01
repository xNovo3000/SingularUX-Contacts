package org.singularux.contacts.data.contacts

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.singularux.contacts.core.permission.PermissionManager
import org.singularux.contacts.data.contacts.repository.ContactBriefRepository
import org.singularux.contacts.data.contacts.repository.ContactBriefRepositoryAndroid

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataContactsModule {

    @Provides
    @ActivityRetainedScoped
    fun providesContactsObserver(): ContactsObserver {
        return ContactsObserver()
    }

    @Provides
    @ActivityRetainedScoped
    fun providesContactBriefRepository(
        @ApplicationContext context: Context,
        permissionManager: PermissionManager,
        contactsObserver: ContactsObserver
    ): ContactBriefRepository {
        return ContactBriefRepositoryAndroid(
            context = context,
            permissionManager = permissionManager,
            contactsObserver = contactsObserver
        )
    }

}