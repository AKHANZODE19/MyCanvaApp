package com.example.mycanvaapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycanvaapp.HomeFragment;
import com.example.mycanvaapp.ImageDetailFragment;
import com.example.mycanvaapp.R;
import com.example.mycanvaapp.models.ParentItem;
import com.example.mycanvaapp.ImageDetailFragment;


import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ParentViewHolder> {

    private final List<ParentItem> parentItemList;
    private final HomeFragment homeFragment; // Reference to HomeFragment
    private final OnItemClickListener onItemClickListener; // Interface for item clicks

    public ParentAdapter(List<ParentItem> parentItemList, HomeFragment homeFragment, OnItemClickListener listener) {
        this.parentItemList = parentItemList;
        this.homeFragment = homeFragment; // Store the reference
        this.onItemClickListener = listener; // Use the passed listener
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        ParentItem parentItem = parentItemList.get(position);

        // Set the image for the parent item
        holder.parentImage.setImageResource(parentItem.getImageResource());

        // Handle image click to open ImageDetailFragment
        holder.parentImage.setOnClickListener(v -> {

            // Use activity's fragment manager for navigation
            Log.d("ParentAdapter", "Image clicked: " + R.drawable.ic_poster_placeholder);
         //  onItemClickListener.onItemClick(position);
            homeFragment.requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ImageDetailFragment.newInstance(parentItem.getImageResource())) // Pass the image resource
                    .addToBackStack(null)
                    .commit();
            Log.d("ParentAdapter", "Fragment transaction committed");
        });


        // Set up RecyclerViews with the OnItemClickListener passed to the adapters
        setupRecyclerView(holder.categoryRecyclerView, parentItem.getCategoryItems(), new CategoryAdapter(parentItem.getCategoryItems(), onItemClickListener));
        setupRecyclerView(holder.postersRecyclerView, parentItem.getPostersItems(), new PostersItemAdapter(parentItem.getPostersItems(), homeFragment.getContext(), onItemClickListener));
        setupRecyclerView(holder.resumesRecyclerView, parentItem.getResumesItems(), new ResumesItemAdapter(parentItem.getResumesItems(), homeFragment.getContext(), onItemClickListener));
        setupRecyclerView(holder.instagramPostsRecyclerView, parentItem.getInstagramPostsItems(), new InstagramPostsItemAdapter(parentItem.getInstagramPostsItems(), homeFragment.getContext(), onItemClickListener));
        setupRecyclerView(holder.phoneWallpapersRecyclerView, parentItem.getPhoneWallpapersItems(), new PhoneWallpapersItemAdapter(parentItem.getPhoneWallpapersItems(), homeFragment.getContext(), onItemClickListener));
        setupRecyclerView(holder.docsRecyclerView, parentItem.getDocsItems(), new DocsItemAdapter(parentItem.getDocsItems(), homeFragment.getContext(), onItemClickListener));
        setupRecyclerView(holder.logosRecyclerView, parentItem.getLogosItems(), new LogosItemAdapter(parentItem.getLogosItems(), homeFragment.getContext(), onItemClickListener));
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<?> items, RecyclerView.Adapter adapter) {
        if (items != null && !items.isEmpty()) {
            recyclerView.setLayoutManager(new LinearLayoutManager(homeFragment.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return parentItemList.size();
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {

        ImageView parentImage;
        RecyclerView categoryRecyclerView;
        RecyclerView postersRecyclerView;
        RecyclerView resumesRecyclerView;
        RecyclerView instagramPostsRecyclerView;
        RecyclerView phoneWallpapersRecyclerView;
        RecyclerView docsRecyclerView;
        RecyclerView logosRecyclerView;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            parentImage = itemView.findViewById(R.id.header_title);
            categoryRecyclerView = itemView.findViewById(R.id.category_recycler_view);
            postersRecyclerView = itemView.findViewById(R.id.postersRecyclerView);
            resumesRecyclerView = itemView.findViewById(R.id.ResumesRecyclerView);
            instagramPostsRecyclerView = itemView.findViewById(R.id.Instagram_PostsRecyclerView);
            phoneWallpapersRecyclerView = itemView.findViewById(R.id.Phone_Wallpapers_RecyclerView);
            docsRecyclerView = itemView.findViewById(R.id.Docs_RecyclerView);
            logosRecyclerView = itemView.findViewById(R.id.Logos_RecyclerView);
        }
    }

    // Define the interface for item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
