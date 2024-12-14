package org.singularux.contacts.data.profile

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import org.singularux.contacts.core.permission.PermissionManager
import org.singularux.contacts.data.contacts.ContactsObserver
import org.singularux.contacts.data.profile.repository.ProfileBriefRepository
import org.singularux.contacts.data.profile.repository.ProfileBriefRepositoryAndroid

@Module
@InstallIn(ActivityRetainedScoped::class)
object DataProfileModule {

    @Provides
    @ActivityRetainedScoped
    fun providesProfileBriefRepository(
        @ApplicationContext context: Context,
        permissionManager: PermissionManager,
        contactsObserver: ContactsObserver
    ): ProfileBriefRepository {
        return ProfileBriefRepositoryAndroid(
            context = context,
            permissionManager = permissionManager,
            contactsObserver = contactsObserver
        )
    }

}