package com.example.mycanvaapp.Adapter;

// ImageAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycanvaapp.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Integer> imageList;
    private final ImageClickListener imageClickListener;

    public interface ImageClickListener {
        void onImageClicked(int imageResId);
    }

    public ImageAdapter(ImageClickListener listener, List<Integer> imageList) {
        this.imageClickListener = listener;
        // Populate this list with your image resources
        this.imageList = List.of(R.drawable.image1, R.drawable.image2, R.drawable.image3);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageResId = imageList.get(position);
        holder.imageView.setImageResource(imageResId);
        holder.imageView.setOnClickListener(v -> imageClickListener.onImageClicked(imageResId));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
