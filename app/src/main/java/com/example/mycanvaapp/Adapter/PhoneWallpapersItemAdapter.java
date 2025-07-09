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
import com.example.mycanvaapp.models.PhoneWallpapersItem;

import java.util.List;

public class PhoneWallpapersItemAdapter extends RecyclerView.Adapter<PhoneWallpapersItemAdapter.WallpaperViewHolder> {

    private final List<PhoneWallpapersItem> wallpapersItemList;
    private final Context context;
    private final ParentAdapter.OnItemClickListener onItemClickListener;

    public PhoneWallpapersItemAdapter(List<PhoneWallpapersItem> wallpapersItemList, Context context, ParentAdapter.OnItemClickListener listener) {
        this.wallpapersItemList = wallpapersItemList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phone_wallpapers, parent, false);
        return new WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        PhoneWallpapersItem item = wallpapersItemList.get(position);

        // Check for valid image resource ID
        int imageResId = item.getImage();
        if (imageResId > 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.ic_phonewallpaper_placeholder); // Fallback image
        }

       // holder.name.setText(item.getName());

        // Pass image resource ID on click
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(imageResId));
    }

    @Override
    public int getItemCount() {
        return wallpapersItemList.size();
    }

    public static class WallpaperViewHolder extends RecyclerView.ViewHolder {
      //  TextView name;
        ImageView image;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
          //  name = itemView.findViewById(R.id.wallpaper_name);
            image = itemView.findViewById(R.id.wallpaper_image);
        }
    }
}
