package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

// Real-time camera scanning model.
public class OcrCameraModel {
    private CameraSource cameraSource;
    private Bitmap bitmapImage = null;
    private Uri resultUri;
    private byte[] imageBytes = null;
    private Context  context;
    private TextRecognizer textRecognizer;
    // Get permission id
    final private int RequestCameraPermissionID = 1001;
    final private int RequestWriteExternalStorageID = 1002;

    // Constructor
    public OcrCameraModel(final Context ctx) {
        this.context = ctx;
    }

    //Getters and setters
    public CameraSource getCameraSource() {
        return cameraSource;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public Uri getResultUri() {
        return resultUri;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public int getRequestCameraPermissionID() {
        return RequestCameraPermissionID;
    }

    public int getRequestWriteExternalStorageID() {
        return RequestWriteExternalStorageID;
    }

    public Context getContext() {
        return context;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public void setResultUri(Uri resultUri) {
        this.resultUri = resultUri;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public void setCameraSource(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
    }

    public TextRecognizer getTextRecognizer() {
        return textRecognizer;
    }

    public void setTextRecognizer(TextRecognizer textRecognizer) {
        this.textRecognizer = textRecognizer;
    }
}
