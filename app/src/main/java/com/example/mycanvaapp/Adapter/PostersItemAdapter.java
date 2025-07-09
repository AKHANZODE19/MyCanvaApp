package com.example.mycanvaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycanvaapp.R;
import com.example.mycanvaapp.models.PostersItem;

import java.util.List;


public class PostersItemAdapter extends RecyclerView.Adapter<PostersItemAdapter.PosterViewHolder> {

    private final List<PostersItem> postersItemList;
    private final Context context;
    private final ParentAdapter.OnItemClickListener onItemClickListener;

    // Constructor updated to accept OnItemClickListener
    public PostersItemAdapter(List<PostersItem> postersItemList, Context context, ParentAdapter.OnItemClickListener listener) {
        this.postersItemList = postersItemList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_posters, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        PostersItem item = postersItemList.get(position);

        holder.posterImage.setImageResource(item.getImage());

        // Set click listener to notify the parent when an item is clicked
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(item.getImage()));
    }

    @Override
    public int getItemCount() {
        return postersItemList.size();
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImage;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.poster_image);
        }
    }


}
