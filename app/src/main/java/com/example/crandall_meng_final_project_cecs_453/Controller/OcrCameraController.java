package com.example.crandall_meng_final_project_cecs_453.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.crandall_meng_final_project_cecs_453.Model.OcrCameraModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OcrCameraController {
    private OcrCameraModel cameraModel;

    public OcrCameraController(Context appContext) {
        cameraModel = new OcrCameraModel(appContext);
    }

    public void setupOcrCamera() {
        setTextRecognizer(new TextRecognizer.Builder(getContext()).build());
        if (!getTextRecognizer().isOperational()) {
            Toast.makeText(getContext(), "Error: Text Recognizer not operational.", Toast.LENGTH_SHORT).show();
        } else {
            cameraModel.setCameraSource(new CameraSource.Builder(getContext(), getTextRecognizer())
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build());
        }
    }

    public void takePicture() {
        getCameraSource().takePicture(null, new CameraSource.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes) {
                getCameraSource().stop();
                setImageBytes(bytes);
                //showPopup();

            }
        });
    }

    public void startCamera(SurfaceView surfaceView) {
        try {
            getCameraSource().start(surfaceView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveImage(byte[] bytes) {
        setBitmapImage(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        //Rotate image 90 degrees
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        setBitmapImage(Bitmap.createBitmap(getBitmapImage(), 0, 0, getBitmapImage().getWidth(), getBitmapImage().getHeight(), matrix, true));
        setResultUri(getImageUri(getBitmapImage()));
    }

    //Convert Bitmap to Uri and saves image to user's external storage.
    public Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public Bitmap getBitmapImage() {
        return cameraModel.getBitmapImage();
    }

    public CameraSource getCameraSource() {
        return cameraModel.getCameraSource();
    }

    public Uri getResultUri() {
        return cameraModel.getResultUri();
    }

    public byte[] getImageBytes() {
        return cameraModel.getImageBytes();
    }

    public int getRequestCameraPermissionID() {
        return cameraModel.getRequestCameraPermissionID();
    }

    public int getRequestWriteExternalStorageID() {
        return cameraModel.getRequestWriteExternalStorageID();
    }

    public Context getContext() {
        return cameraModel.getContext();
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        cameraModel.setBitmapImage(bitmapImage);
    }

    public void setResultUri(Uri resultUri) {
        cameraModel.setResultUri(resultUri);
    }

    public void setImageBytes(byte[] imageBytes) {
        cameraModel.setImageBytes(imageBytes);
    }
    public void setCameraSource(CameraSource cameraSource) {
        cameraModel.setCameraSource(cameraSource);
    }

    public TextRecognizer getTextRecognizer() {
        return cameraModel.getTextRecognizer();
    }

    public void setTextRecognizer(TextRecognizer textRecognizer) {
        cameraModel.setTextRecognizer(textRecognizer);
    }
}
