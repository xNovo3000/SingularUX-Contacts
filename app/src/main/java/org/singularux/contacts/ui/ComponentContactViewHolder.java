package org.singularux.contacts.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.R;
import org.singularux.contacts.databinding.ComponentContactBinding;

import java.util.Optional;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.val;

public class ComponentContactViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_ID = 101;

    public final ShapeableImageView avatarImage;
    public final MaterialTextView avatarText, headline;

    private @Nullable Disposable avatarImageLoad = null;

    public ComponentContactViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ComponentContactBinding.bind(itemView);
        this.avatarImage = binding.componentContactAvatarImage;
        this.avatarText = binding.componentContactAvatarText;
        this.headline = binding.componentContactHeadline;
    }

    public void onBindViewHolder(@NonNull ComponentContactData data,
                                 @NonNull Scheduler ioScheduler,
                                 ContactThumbnailCache contactThumbnailCache
    ) {
        // Calculate first character and capitalize it
        char firstCharacter = 0x00B7;
        if (!data.getDisplayName().isEmpty()) {
            firstCharacter = Character.toUpperCase(data.getDisplayName().charAt(0));
        }
        // Set avatar character and headline
        avatarText.setText(String.valueOf(firstCharacter));
        headline.setText(data.getDisplayName());
        // Load avatar async
        if (data.getThumbnailPath() != null) {
            avatarImageLoad = Maybe.fromCallable(() -> Optional.ofNullable(contactThumbnailCache.get(data.getThumbnailPath())))
                    .subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(maybeBitmap -> maybeBitmap.ifPresent(avatarImage::setImageBitmap));
        } else {
            avatarImage.setImageBitmap(null);
        }
    }

    public void onViewRecycled() {
        if (avatarImageLoad != null) {
            // Cancel image loading
            avatarImageLoad.dispose();
            avatarImageLoad = null;
        }
    }

    public static @NonNull ComponentContactViewHolder create(@NonNull ViewGroup parent) {
        return new ComponentContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_contact, parent, false));
    }

}
