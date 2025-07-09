package com.example.mycanvaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycanvaapp.R;
import com.example.mycanvaapp.models.InstagramPostsItem;

import java.util.List;

public class InstagramPostsItemAdapter extends RecyclerView.Adapter<InstagramPostsItemAdapter.InstagramPostViewHolder> {

    private final List<InstagramPostsItem> instagramPostsItemList;
    private final Context context;
    private final ParentAdapter.OnItemClickListener onItemClickListener;

    public InstagramPostsItemAdapter(List<InstagramPostsItem> instagramPostsItemList, Context context, ParentAdapter.OnItemClickListener listener) {
        this.instagramPostsItemList = instagramPostsItemList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public InstagramPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_instagram_posts, parent, false);
        return new InstagramPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstagramPostViewHolder holder, int position) {
        InstagramPostsItem item = instagramPostsItemList.get(position);

        // Check for a valid image resource ID
        int imageResId = item.getImage();
        if (imageResId > 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.ic_instagram_placeholder); // Use a fallback image
        }

      //  holder.name.setText(item.getCaption());

        // Pass the image resource ID instead of the position on click
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(imageResId));
    }

    @Override
    public int getItemCount() {
        return instagramPostsItemList.size();
    }

    public static class InstagramPostViewHolder extends RecyclerView.ViewHolder {
      //  TextView name;
        ImageView image;

        public InstagramPostViewHolder(@NonNull View itemView) {
            super(itemView);
        //    name = itemView.findViewById(R.id.instagram_post_name);
            image = itemView.findViewById(R.id.instagram_post_image);
        }
    }
}
