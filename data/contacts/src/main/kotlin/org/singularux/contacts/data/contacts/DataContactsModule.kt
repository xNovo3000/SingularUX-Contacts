package org.singularux.contacts.data.contacts

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.singularux.contacts.core.permission.ContactsPermissionManager
import org.singularux.contacts.data.contacts.repository.ContactsRepository
import org.singularux.contacts.data.contacts.repository.ContactsRepositoryAndroid

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataContactsModule {

    @Provides
    @ActivityRetainedScoped
    fun bindsContactsRepository(
        @ApplicationContext context: Context,
        contactsPermissionManager: ContactsPermissionManager
    ): ContactsRepository {
        return ContactsRepositoryAndroid(
            context = context,
            contactsPermissionManager = contactsPermissionManager
        )
    }

}