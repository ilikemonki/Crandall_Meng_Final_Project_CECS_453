package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

public class OcrCameraModel {
    private CameraSource cameraSource;
    private Bitmap bitmapImage = null;
    private Uri resultUri;
    private byte[] imageBytes = null;
    final private int RequestCameraPermissionID = 1001;
    final private int RequestWriteExternalStorageID = 1002;
    private Context  context;
    private TextRecognizer textRecognizer;

    public OcrCameraModel(final Context ctx) {
        this.context = ctx;
    }

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
