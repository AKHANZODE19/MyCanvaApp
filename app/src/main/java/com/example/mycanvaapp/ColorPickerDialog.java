package com.example.mycanvaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

public class ColorPickerDialog {
    private final Context context;
    private final ColorPickerDialogListener listener;

    // Array of color names and their corresponding hex codes
    private final String[] colorNames = {
            "Red", "Green", "Blue", "Yellow", "Magenta", "Cyan", "White", "Black"
    };

    private final String[] colorCodes = {
            "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#FFFFFF", "#000000"
    };

    public ColorPickerDialog(Context context, ColorPickerDialogListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a color")
                .setItems(colorNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onColorSelected(Color.parseColor(colorCodes[which]));
                    }
                })
                .show();
    }

    public interface ColorPickerDialogListener {
        void onColorSelected(int color);
    }
}
