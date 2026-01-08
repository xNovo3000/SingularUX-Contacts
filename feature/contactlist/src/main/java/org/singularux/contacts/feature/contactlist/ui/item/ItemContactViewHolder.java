package org.singularux.contacts.feature.contactlist.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.singularux.contacts.feature.contactlist.R;
import org.singularux.contacts.feature.contactlist.databinding.ItemContactBinding;
import org.singularux.contacts.feature.contactlist.ui.behavior.ItemContactGoToContactOnClick;
import org.singularux.contacts.feature.contactlist.ui.util.ContactThumbnailCache;

import java.util.Optional;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.val;

public class ItemContactViewHolder extends RecyclerView.ViewHolder {

    public static final int VIEW_TYPE_ID = 101;

    public final ShapeableImageView avatarImage;
    public final MaterialTextView avatarText, headline;

    private @Nullable Disposable avatarImageLoad = null;

    public ItemContactViewHolder(@NonNull View itemView) {
        super(itemView);
        val binding = ItemContactBinding.bind(itemView);
        this.avatarImage = binding.itemContactAvatarImage;
        this.avatarText = binding.itemContactAvatarText;
        this.headline = binding.itemContactHeadline;
    }

    public void onBindViewHolder(@NonNull ItemContactData data,
                                 @NonNull Scheduler ioScheduler,
                                 ContactThumbnailCache contactThumbnailCache
    ) {
        // Calculate first character and capitalize it
        char firstCharacter = 0x00B7;
        if (!data.getDisplayName().isBlank()) {
            firstCharacter = Character.toUpperCase(data.getDisplayName().charAt(0));
        }
        // Set avatar character and headline
        avatarText.setText(String.valueOf(firstCharacter));
        headline.setText(data.getDisplayName());
        itemView.setOnClickListener(new ItemContactGoToContactOnClick(data.getLookupKey()));
        // Load avatar async
        if (data.getThumbnailPath() != null) {
            avatarImageLoad = Maybe.fromCallable(() -> Optional.ofNullable(contactThumbnailCache.get(data.getThumbnailPath())))
                    .subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(maybeBitmap -> maybeBitmap.ifPresent(avatarImage::setImageBitmap));
        } else {
            avatarImage.setImageResource(0);
        }
    }

    public void onViewRecycled() {
        if (avatarImageLoad != null) {
            // Cancel image loading
            avatarImageLoad.dispose();
            avatarImageLoad = null;
        }
    }

    public static @NonNull ItemContactViewHolder create(@NonNull ViewGroup parent) {
        return new ItemContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false));
    }

}
