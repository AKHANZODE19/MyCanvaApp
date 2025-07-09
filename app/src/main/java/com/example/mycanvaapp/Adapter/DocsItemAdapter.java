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
import com.example.mycanvaapp.models.DocsItem;

import java.util.List;

public class DocsItemAdapter extends RecyclerView.Adapter<DocsItemAdapter.DocsViewHolder> {

    private final List<DocsItem> docsItemList;
    private final Context context;
    private final ParentAdapter.OnItemClickListener onItemClickListener;

    public DocsItemAdapter(List<DocsItem> docsItemList, Context context, ParentAdapter.OnItemClickListener listener) {
        this.docsItemList = docsItemList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DocsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_docs, parent, false);
        return new DocsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocsViewHolder holder, int position) {
        DocsItem item = docsItemList.get(position);

        // Check for valid image resource ID
        int imageResId = item.getImage();
        if (imageResId > 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.ic_document_placeholder); // Fallback image
        }

       // holder.name.setText(item.getName());

        // Pass image resource ID on click
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(imageResId));
    }

    @Override
    public int getItemCount() {
        return docsItemList.size();
    }

    public static class DocsViewHolder extends RecyclerView.ViewHolder {
       // TextView name;
        ImageView image;

        public DocsViewHolder(@NonNull View itemView) {
            super(itemView);
          //  name = itemView.findViewById(R.id.doc_name);
            image = itemView.findViewById(R.id.doc_image);
        }
    }
}
