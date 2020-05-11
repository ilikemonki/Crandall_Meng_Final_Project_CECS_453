package com.example.crandall_meng_final_project_cecs_453.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import androidx.fragment.app.Fragment;

//Image to Text Model
//Implements cropping images
public class ImageToTextModel {
    private BitmapDrawable bitmapDrawable;
    private Bitmap bitmap;
    private Uri resultUri;
    private Context appContext;
    private Fragment appFragment;

    // Constructor
    public ImageToTextModel(final Context ctx, final Fragment fragment) {
        this.appContext = ctx;
        this.appFragment = fragment;
    }

    //Getters and setters
    public Context getAppContext() {
        return appContext;
    }

    public BitmapDrawable getBitmapDrawable() {
        return bitmapDrawable;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Uri getResultUri() {
        return resultUri;
    }

    public Fragment getAppFragment() {
        return appFragment;
    }

    public void setBitmapDrawable(BitmapDrawable bitmapDrawable) {
        this.bitmapDrawable = bitmapDrawable;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setResultUri(Uri resultUri) {
        this.resultUri = resultUri;
    }

}
