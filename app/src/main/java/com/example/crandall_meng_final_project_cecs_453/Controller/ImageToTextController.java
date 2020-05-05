package com.example.crandall_meng_final_project_cecs_453.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.crandall_meng_final_project_cecs_453.Model.ImageToTextModel;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;

public class ImageToTextController {
    private ImageToTextModel ittModel;

    public ImageToTextController(Context appContext, Fragment fragment) {
        ittModel = new ImageToTextModel(appContext, fragment);
    }

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

    public void startCropImage(Uri imageUri) {
        CropImage.activity(imageUri).start(ittModel.getAppContext(), ittModel.getAppFragment());
    }

    public void startCropImage() {
        CropImage.activity().start(ittModel.getAppContext(), ittModel.getAppFragment());
    }

    public void openImageSourceUI() {
        CropImage.startPickImageActivity(ittModel.getAppContext(), ittModel.getAppFragment());
    }

    public Uri getPickedImageSource(Intent data) {
        return CropImage.getPickImageResultUri(ittModel.getAppContext(), data);
    }

    public ImageToTextModel getIttModel() {
        return ittModel;
    }

}
