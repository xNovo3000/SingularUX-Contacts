package org.singularux.contacts.feature.contactview.ui.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import org.singularux.contacts.feature.contactview.R;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class ContactPhotoCache extends LruCache<Uri, Bitmap> {

    private static final String TAG = "ContactPhotoCache";

    private static final int CACHE_SIZE_BYTES = 1;  // Only one element at a time

    private final Context context;
    private final Size size;

    @Inject
    public ContactPhotoCache(@ApplicationContext Context context) {
        super(CACHE_SIZE_BYTES);
        this.context = context;
        // Density will not change, safe to get the dimension only one time
        int sizePx = (int) context.getResources()
                .getDimension(R.dimen.contact_view_content_avatar_size_max);
        this.size = new Size(sizePx, sizePx);
    }

    @Override
    protected @Nullable Bitmap create(@NonNull Uri key) {
        try {
            return context.getContentResolver().loadThumbnail(key, size, null);
        } catch (IOException e) {
            Log.e(TAG, "Cannot load from path " + key);
            return null;
        }
    }

}
