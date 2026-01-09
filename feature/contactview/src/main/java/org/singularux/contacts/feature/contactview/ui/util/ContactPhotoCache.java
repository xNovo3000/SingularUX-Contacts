package org.singularux.contacts.feature.contactview.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import lombok.val;

@ActivityRetainedScoped
public class ContactPhotoCache extends LruCache<Uri, Bitmap> {

    private static final String TAG = "ContactPhotoCache";

    private static final int CACHE_SIZE_BYTES = 1;  // Only one element at a time

    private final Context context;

    @Inject
    public ContactPhotoCache(@ApplicationContext Context context) {
        super(CACHE_SIZE_BYTES);
        this.context = context;
    }

    @Override
    protected @Nullable Bitmap create(@NonNull Uri key) {
        // Open file descriptor
        try (val descriptor = context.getContentResolver()
                .openAssetFileDescriptor(key, "r")) {
            // Check if file descriptor exists
            if (descriptor == null) {
                return null;
            }
            // Parse with bitmap
            return BitmapFactory.decodeFileDescriptor(descriptor.getFileDescriptor());
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File does not exists " + key);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Cannot load from path " + key);
            return null;
        }
    }

}
