package org.singularux.contacts.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import lombok.val;

@ActivityRetainedScoped
public class ContactThumbnailCache extends LruCache<Uri, Bitmap> {

    private static final String TAG = "ContactThumbnailCache";

    private static final int THUMBNAIL_SIZE_DP = 40;
    private static final int CACHE_SIZE_BYTES = 4 * 1024 * 1024;  // 4 MB

    private final Context context;

    @Inject
    public ContactThumbnailCache(@ApplicationContext Context context) {
        super(CACHE_SIZE_BYTES);
        this.context = ContextCompat.getContextForLanguage(context);
    }

    @Override
    protected @Nullable Bitmap create(@NonNull Uri key) {
        float density = context.getResources().getDisplayMetrics().density;
        int sizePx = (int) (THUMBNAIL_SIZE_DP * density);
        val size = new Size(sizePx, sizePx);
        try {
            return context.getContentResolver().loadThumbnail(key, size, null);
        } catch (IOException e) {
            Log.e(TAG, "Cannot load from path " + key);
            return null;
        }
    }

    @Override
    protected int sizeOf(@NonNull Uri key, @NonNull Bitmap value) {
        return value.getByteCount();
    }

}
