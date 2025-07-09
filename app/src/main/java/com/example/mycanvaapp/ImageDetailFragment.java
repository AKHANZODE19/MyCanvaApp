package com.example.mycanvaapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Matrix;
import android.view.ScaleGestureDetector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

public class ImageDetailFragment extends Fragment {

    private static final String ARG_IMAGE_RES_ID = "imageResId";
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private View bottomSheet;
    private ImageView fullScreenImageView;
    private View textFormattingContainer; // Reference for the formatting container
    private int currentImageResId = -1; // Holds the current image ID to avoid reloading
    private EditText currentlyEditingText; // Store the active EditText reference
    private boolean isUnderlined = false; // Store underline state



    public static ImageDetailFragment newInstance(int imageResId) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_detail_fragment, container, false);  // Inflate the layout

        // Initialize the bottom sheet
        initBottomSheet(view);

        // Setup BottomNavigationView listener
        setupBottomNavigationView(view);

        // Handle image loading
        loadImage(view);

        // Setup button click listeners to add different types of text boxes
        setupTextBoxButtons(view);

        // Initialize the text formatting container
        textFormattingContainer = view.findViewById(R.id.text_formatting_container);
        textFormattingContainer.setVisibility(View.GONE); // Initially hide the formatting container
        setupTextFormattingButtons(view);

        return view;
    }

    private void initBottomSheet(View view) {
        bottomSheet = view.findViewById(R.id.bottom_sheet_container);
        bottomSheet.setVisibility(View.GONE);
        if (bottomSheet.getParent() instanceof CoordinatorLayout) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            Log.e("ImageDetailFragment", "BottomSheetBehavior initialization failed.");
        }
    }

    private void setupBottomNavigationView(View view) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_text) {
                toggleBottomSheet(true); // Open the text bottom sheet
                return true;
            } else if (item.getItemId() == R.id.navigation_upload) { // Check if the upload icon is clicked
                openImageUploadBottomSheet(); // Call method to open the upload bottom sheet
                return true;
            }
            return false; // Default return if none of the above cases match
        });
    }

    // Inside your ImageDetailFragment
    private void openImageUploadBottomSheet() {
        List<Integer> imageList = List.of(
                R.drawable.image1, // Your image resources
                R.drawable.image2,
                R.drawable.image3
        );

        ImageUploadBottomSheet uploadBottomSheet = new ImageUploadBottomSheet(imageResId -> {
            // Handle the selected image resource ID here
            displaySelectedImage(imageResId);
        }, imageList); // Pass the image list to the bottom sheet

        // Use getChildFragmentManager() or getFragmentManager()
        uploadBottomSheet.show(getChildFragmentManager(), "uploadBottomSheet");
    }

    // Display selected image logic as an overlay with drag and resize functionality
    private void displaySelectedImage(int imageResId) {
        if (fullScreenImageView != null) {
            // Create a new ImageView for the overlay
            ImageView overlayImageView = new ImageView(getContext());
            overlayImageView.setImageResource(imageResId);

            // Resize overlayImageView to initial 100dp x 100dp
            int initialSizeInDp = 200;
            int initialSizeInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    initialSizeInDp,
                    getResources().getDisplayMetrics()
            );

            // Set layout parameters for the overlay ImageView
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(initialSizeInPx, initialSizeInPx);
            params.leftMargin = 100; // Adjust initial position as needed
            params.topMargin = 100;
            overlayImageView.setLayoutParams(params);

            // Enable dragging and resizing
            enableDragAndResize(overlayImageView);

            // Add the overlay to the same parent as fullScreenImageView
            ((FrameLayout) fullScreenImageView.getParent()).addView(overlayImageView);
        }
    }

    private void enableDragAndResize(ImageView imageView) {
        // Initialize scale factor and ScaleGestureDetector
        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener(imageView));

        // For dragging
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Handle pinch-to-zoom for resizing
                scaleGestureDetector.onTouchEvent(event);

                // Handle dragging
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    default:
                        return scaleGestureDetector.onTouchEvent(event);
                }
                return true;
            }
        });
    }

    // Inner class for pinch-to-zoom functionality
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private final ImageView imageView;

        public ScaleListener(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            Log.d("ScaleListener", "Scale factor: " + scaleFactor);

            // Apply scaling if the scale factor is within a reasonable range
            imageView.setScaleX(scaleFactor * imageView.getScaleX());
            imageView.setScaleY(scaleFactor * imageView.getScaleY());
            return true;
        }
    }


    private void loadImage(View view) {
        Bundle args = getArguments();
        if (args != null) {
            int imageResId = args.getInt(ARG_IMAGE_RES_ID, -1);
            if (imageResId > 0 && imageResId != currentImageResId) {
                fullScreenImageView = view.findViewById(R.id.fullScreenImageView);
                fullScreenImageView.setImageResource(imageResId);
                currentImageResId = imageResId; // Set current image ID
            }
        }
    }

    private void setupTextBoxButtons(View view) {
        Button addTextButton = view.findViewById(R.id.btnAddTextBox);
        Button addHeadingButton = view.findViewById(R.id.btnAddHeading);
        Button addSubheadingButton = view.findViewById(R.id.btnAddSubheading);
        Button addBodyTextButton = view.findViewById(R.id.btnAddBodyText);

        View.OnClickListener addTextListener = v -> {
            if (v instanceof Button) {
                Button button = (Button) v;
                String buttonText = button.getText() != null ? button.getText().toString().toLowerCase() : "Text";
                addTextBox(buttonText, button.getTag() != null ? button.getTag().toString() : "Text"); // Default type if tag is null
                toggleBottomSheet(false); // Close the bottom sheet after adding text
            }
        };

        // Assign the click listener to buttons
        addTextButton.setOnClickListener(addTextListener);
        addHeadingButton.setOnClickListener(addTextListener);
        addSubheadingButton.setOnClickListener(addTextListener);
        addBodyTextButton.setOnClickListener(addTextListener);
    }

    private void toggleBottomSheet(boolean open) {
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(open ? BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_HIDDEN);
            bottomSheet.setVisibility(open ? View.VISIBLE : View.GONE);
            if (!open) {
                // Hide the formatting container when the bottom sheet is closed
                textFormattingContainer.setVisibility(View.GONE);
            }
        } else {
            Log.e("ImageDetailFragment", "BottomSheetBehavior is not initialized.");
        }
    }

    private void addTextBox(String text, String type) {
        final TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(16, 8, 16, 8);
        setTextViewSize(textView, type);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 100; // Customize as needed
        params.topMargin = 100; // Customize as needed
        textView.setLayoutParams(params);

        ((FrameLayout) fullScreenImageView.getParent()).addView(textView);

        textView.setOnTouchListener(new TextTouchListener(textView));
    }

    private void setTextViewSize(TextView textView, String type) {
        switch (type) {
            case "Text":
                textView.setTextSize(20);
                break;
            case "heading":
                textView.setTextSize(30);
                break;
            case "subheading":
                textView.setTextSize(24);
                break;
            case "bodytext":
                textView.setTextSize(16);
                break;
            default:
                textView.setTextSize(18);
        }
    }

    private void setupTextFormattingButtons(View view) {
        AppCompatButton btnBold = view.findViewById(R.id.btnBold);
        AppCompatButton btnItalic = view.findViewById(R.id.btnItalic);
        AppCompatButton btnColor = view.findViewById(R.id.btnColor);
        AppCompatButton btnUnderline = view.findViewById(R.id.btnUnderline);
        AppCompatButton btnIncreaseFontSize = view.findViewById(R.id.btnIncreaseFontSize); // Font size increase button
        AppCompatButton btnDecreaseFontSize = view.findViewById(R.id.btnDecreaseFontSize); // Font size decrease button
        AppCompatButton btnChangeFont = view.findViewById(R.id.btnChangeFont); // Change font button

        // Set up listeners for the formatting buttons
        btnBold.setOnClickListener(v -> applyTextFormatting(currentlyEditingText, Typeface.BOLD));
        btnItalic.setOnClickListener(v -> applyTextFormatting(currentlyEditingText, Typeface.ITALIC));
        btnUnderline.setOnClickListener(v -> toggleUnderline(currentlyEditingText));
        btnColor.setOnClickListener(v -> openColorPicker(currentlyEditingText));
        btnIncreaseFontSize.setOnClickListener(v -> changeFontSize(currentlyEditingText, true));
        btnDecreaseFontSize.setOnClickListener(v -> changeFontSize(currentlyEditingText, false));
        btnChangeFont.setOnClickListener(v -> changeFont(currentlyEditingText));
    }

    // Modify text formatting method for bold and italic
    private void applyTextFormatting(EditText editText, int style) {
        if (editText != null) {
            int currentStyle = editText.getTypeface().getStyle();
            Typeface newTypeface;

            // Apply multiple styles by adding the new style to the existing one
            if (style == Typeface.BOLD) {
                if ((currentStyle & Typeface.BOLD) == Typeface.BOLD) {
                    currentStyle &= ~Typeface.BOLD;
                } else {
                    currentStyle |= Typeface.BOLD;
                }
            } else if (style == Typeface.ITALIC) {
                if ((currentStyle & Typeface.ITALIC) == Typeface.ITALIC) {
                    currentStyle &= ~Typeface.ITALIC;
                } else {
                    currentStyle |= Typeface.ITALIC;
                }
            }

            newTypeface = Typeface.create(editText.getTypeface(), currentStyle);
            editText.setTypeface(newTypeface);
        }
    }


    // Toggle underline method
    private void toggleUnderline(EditText editText) {
        if (editText != null) {
            int flags = editText.getPaintFlags();
            isUnderlined = (flags & Paint.UNDERLINE_TEXT_FLAG) != Paint.UNDERLINE_TEXT_FLAG;
            editText.setPaintFlags(isUnderlined ? flags | Paint.UNDERLINE_TEXT_FLAG : flags & (~Paint.UNDERLINE_TEXT_FLAG));
        }
    }

    private void openColorPicker(EditText editText) {
        if (editText != null) {
            new ColorPickerDialog(editText.getContext(), color -> {
                editText.setTextColor(color);
            }).show();
        }
    }

    // Function to change font size
    private void changeFontSize(EditText editText, boolean increase) {
        if (editText != null) {
            float currentSize = editText.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
            float newSize = increase ? currentSize + 2 : currentSize - 2;
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, newSize);
        }
    }

    // Function to change font
    private void changeFont(EditText editText) {
        if (editText != null) {
            // Available fonts
            final String[] fontNames = {"Sans Serif", "Monospace", "Serif", "CustomFont1", "CustomFont2"};
            final Typeface[] fonts = {
                    Typeface.SANS_SERIF,
                    Typeface.MONOSPACE,
                    Typeface.SERIF,
                    Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Italic.ttf"),
                    //Typeface.createFromAsset(getContext().getAssets(), "fonts/custom_font2.ttf")
            };

            // Create and show a font picker dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose Font");
            builder.setItems(fontNames, (dialog, which) -> {
                editText.setTypeface(fonts[which]);
            });
            builder.show();
        }
    }


    private class TextTouchListener implements View.OnTouchListener {
        private float dX, dY;
        private long startClickTime;
        private static final int MAX_CLICK_DURATION = 150;

        private final View textView;

        public TextTouchListener(View textView) {
            this.textView = textView;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    startClickTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    view.animate().x(event.getRawX() + dX).y(event.getRawY() + dY).setDuration(0).start();
                    break;
                case MotionEvent.ACTION_UP:
                    long clickDuration = System.currentTimeMillis() - startClickTime;
                    if (clickDuration < MAX_CLICK_DURATION) {
                        enterTextEditMode((TextView) textView);
                    }
                    break;
            }
            return true;
        }
    }

    private void enterTextEditMode(TextView textView) {
        textView.setVisibility(View.GONE);

        EditText editText = new EditText(getContext());
        editText.setText(textView.getText());
        editText.setTextColor(textView.getCurrentTextColor());

        float textSizeInPx = textView.getTextSize();
        float textSizeInSp = textSizeInPx / getResources().getDisplayMetrics().scaledDensity;
        editText.setTextSize(textSizeInSp); // Set size in SP

        editText.setTypeface(textView.getTypeface());
        editText.setPadding(16, 8, 16, 8);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
        editText.setLayoutParams(params);

        ((FrameLayout) textView.getParent()).addView(editText);
        editText.requestFocus();
        showSoftKeyboard(editText);

        textFormattingContainer.setVisibility(View.VISIBLE);
        currentlyEditingText = editText;

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                saveEditedText(editText, textView);

                // Hide the Bottom Sheet when editing is done
                toggleBottomSheet(false);

                return true;
            }
            return false;
        });
    }


    private void saveEditedText(EditText editText, TextView textView) {
        textView.setText(editText.getText());
        textView.setTextColor(editText.getCurrentTextColor());

        float textSizeInSp = editText.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        textView.setTextSize(textSizeInSp); // Set size in SP


        textView.setTypeface(editText.getTypeface());

        if ((editText.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != 0) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }

        ((FrameLayout) editText.getParent()).removeView(editText);
        textView.setVisibility(View.VISIBLE);

        textFormattingContainer.setVisibility(View.GONE);
        currentlyEditingText = null;

        hideSoftKeyboard(editText);
    }

    private void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
