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
import com.example.mycanvaapp.models.LogosItem;

import java.util.List;

public class LogosItemAdapter extends RecyclerView.Adapter<LogosItemAdapter.LogosViewHolder> {

    private final List<LogosItem> logosItemList;
    private final Context context;
    private final ParentAdapter.OnItemClickListener onItemClickListener;

    public LogosItemAdapter(List<LogosItem> logosItemList, Context context, ParentAdapter.OnItemClickListener listener) {
        this.logosItemList = logosItemList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public LogosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_logos, parent, false);
        return new LogosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogosViewHolder holder, int position) {
        LogosItem item = logosItemList.get(position);

        // Check for valid image resource ID
        int imageResId = item.getImage();
        if (imageResId > 0) {
            holder.imageView.setImageResource(imageResId);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_logo_r); // Fallback image
        }

      //  holder.nameTextView.setText(item.getName());

        // Pass image resource ID on click
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(imageResId));
    }

    @Override
    public int getItemCount() {
        return logosItemList.size();
    }

    public static class LogosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
       // TextView nameTextView;

        public LogosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.logo_image);
           // nameTextView = itemView.findViewById(R.id.logo_name);
        }
    }
}
