// ImageUploadBottomSheet.java
package com.example.mycanvaapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mycanvaapp.Adapter.ImageAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;

public class ImageUploadBottomSheet extends BottomSheetDialogFragment {
    private final ImageClickListener imageClickListener;
    private final List<Integer> imageList; // List of image resource IDs

    public interface ImageClickListener {
        void onImageSelected(int imageResId);
    }

    // Constructor accepting both listener and image list
    public ImageUploadBottomSheet(ImageClickListener listener, List<Integer> images) {
        this.imageClickListener = listener;
        this.imageList = images; // Store the image list
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_upload_bottom_sheet, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.image_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Pass the image list to the adapter
        ImageAdapter adapter = new ImageAdapter(this::onImageClicked ,imageList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void onImageClicked(int imageResId) {
        if (imageClickListener != null) {
            imageClickListener.onImageSelected(imageResId);
        }
        dismiss();
    }
}
