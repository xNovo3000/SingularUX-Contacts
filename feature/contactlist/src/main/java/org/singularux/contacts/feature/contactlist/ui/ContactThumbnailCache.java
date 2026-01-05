package org.singularux.contacts.feature.contactlist.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import org.singularux.contacts.feature.contactlist.R;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped  // UI only, if something changes, the cache is cleared with the application
public class ContactThumbnailCache extends LruCache<Uri, Bitmap> {

    private static final String TAG = "ContactThumbnailCache";

    private static final int CACHE_SIZE_BYTES = 4 * 1024 * 1024;  // 4 MB

    private final Context context;
    private final Size size;

    @Inject
    public ContactThumbnailCache(@ApplicationContext Context context) {
        super(CACHE_SIZE_BYTES);
        this.context = context;
        // Density will not change, safe to get the dimension only one time
        int sizePx = (int) context.getResources().getDimension(R.dimen.item_contact_avatar);
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

    @Override
    protected int sizeOf(@NonNull Uri key, @NonNull Bitmap value) {
        return value.getByteCount();
    }

}
