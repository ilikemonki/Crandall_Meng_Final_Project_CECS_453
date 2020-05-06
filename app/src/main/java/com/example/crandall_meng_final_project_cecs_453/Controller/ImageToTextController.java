package com.example.crandall_meng_final_project_cecs_453.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.crandall_meng_final_project_cecs_453.Model.ImageToTextModel;
import com.example.crandall_meng_final_project_cecs_453.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class ImageToTextController {
    private ImageToTextModel ittModel;

    //Constructor
    public ImageToTextController(Context appContext, Fragment fragment) {
        ittModel = new ImageToTextModel(appContext, fragment);
    }

    //Read text from image, then return the text as StringBuilder
    public StringBuilder getTextFromImage(Bitmap bitMap) {
        StringBuilder stringBuilder = new StringBuilder();
        TextRecognizer textRecognizer = new TextRecognizer.Builder(ittModel.getAppContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(ittModel.getAppContext(), "Error: Text Recognizer not operational.", Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(bitMap).build();
            SparseArray<TextBlock> textBlockSparseArray = textRecognizer.detect(frame);

            for (int i = 0; i < textBlockSparseArray.size(); i++) {
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            //Print error if characters cannot be recognized.
            if (stringBuilder.toString().equals("")) {
                stringBuilder.append("Not recognizable.");
            }
        }
        return stringBuilder;
    }

    //Save image to storage
    public void saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        MediaStore.Images.Media.insertImage(ittModel.getAppContext().getContentResolver(), bitmap, "Title", null);
    }

    //Crops the image that is passed in the parameter
    public void startCropImage(Uri imageUri) {
        CropImage.activity(imageUri).start(ittModel.getAppContext(), ittModel.getAppFragment());
    }

    //Opens image source
    public void openImageSourceUI() {
        CropImage.startPickImageActivity(ittModel.getAppContext(), ittModel.getAppFragment());
    }

    //returns the Uri from the picked image and image source
    public Uri getPickedImageUri(Intent data) {
        return CropImage.getPickImageResultUri(ittModel.getAppContext(), data);
    }

    //Show alert dialog when saving image.
    public void saveImageAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(ittModel.getAppContext())
                .setTitle("Save this image?")
                .setMessage("This image will be saved into your gallery.")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveImage(getBitmap());
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_menu_save)
                .show();
    }

    //Getters and setters
    public BitmapDrawable getBitmapDrawable() {
        return ittModel.getBitmapDrawable();
    }

    public Bitmap getBitmap() {
        return ittModel.getBitmap();
    }

    public Uri getResultUri() {
        return ittModel.getResultUri();
    }

    public Fragment getAppFragment() {
        return ittModel.getAppFragment();
    }

    public void setBitmapDrawable(BitmapDrawable bitmapDrawable) {
        ittModel.setBitmapDrawable(bitmapDrawable);
    }

    public void setBitmap(Bitmap bitmap) {
        ittModel.setBitmap(bitmap);
    }

    public void setResultUri(Uri resultUri) {
        ittModel.setResultUri(resultUri);
    }
}
