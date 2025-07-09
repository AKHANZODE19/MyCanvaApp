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
import com.example.mycanvaapp.models.ResumesItem;

import java.util.List;

public class ResumesItemAdapter extends RecyclerView.Adapter<ResumesItemAdapter.ResumeViewHolder> {

    private final List<ResumesItem> resumesItemList;
    private final Context context;
    private final ParentAdapter.OnItemClickListener onItemClickListener;

    public ResumesItemAdapter(List<ResumesItem> resumesItemList, Context context, ParentAdapter.OnItemClickListener listener) {
        this.resumesItemList = resumesItemList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ResumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resumes, parent, false);
        return new ResumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumeViewHolder holder, int position) {
        ResumesItem item = resumesItemList.get(position);

        // Set name of the resume item
       // holder.name.setText(item.getName());

        // Check if the image resource ID is valid before setting it
        int imageResId = item.getImage();
        if (imageResId > 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.ic_resume_placeholder); // Fallback image in case of invalid ID
        }

        // Pass the image resource ID on click, not the position
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(imageResId));
    }

    @Override
    public int getItemCount() {
        return resumesItemList.size();
    }

    public static class ResumeViewHolder extends RecyclerView.ViewHolder {
      //  TextView name;
        ImageView image;

        public ResumeViewHolder(@NonNull View itemView) {
            super(itemView);
          //  name = itemView.findViewById(R.id.resume_name);
            image = itemView.findViewById(R.id.resume_image);
        }
    }
}
