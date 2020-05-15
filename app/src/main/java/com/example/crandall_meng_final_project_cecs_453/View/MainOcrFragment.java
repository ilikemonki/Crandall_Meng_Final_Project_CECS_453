package com.example.crandall_meng_final_project_cecs_453.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crandall_meng_final_project_cecs_453.Controller.ImageToTextController;
import com.example.crandall_meng_final_project_cecs_453.Controller.TextToSpeechController;
import com.example.crandall_meng_final_project_cecs_453.R;
import com.theartofdev.edmodo.cropper.CropImage;

import static android.app.Activity.RESULT_OK;

// Main page after login.
// Shows image, text from image, buttons for images, save image, and text to speech button.
public class MainOcrFragment extends Fragment {
    private ImageView mImageView;
    private TextView mShowText;
    private ImageButton mGetImageButton;
    private ImageButton mTextToSpeechButton;
    private ImageButton mOcrButton;
    private ImageButton mSaveImageButton;

    //Controllers
    private ImageToTextController ittController;
    private TextToSpeechController ttsController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_main_ocr, container, false);

        ittController = new ImageToTextController(getContext(), this);
        ttsController = new TextToSpeechController(getContext());

        mImageView= rootView.findViewById(R.id.img_imageview);
        mShowText= rootView.findViewById(R.id.txt_show_text);
        mGetImageButton = rootView.findViewById(R.id.btn_getImage);
        mTextToSpeechButton = rootView.findViewById(R.id.btn_speaker);
        mOcrButton = rootView.findViewById(R.id.btn_ocrCamera);
        mSaveImageButton = rootView.findViewById(R.id.btn_saveImage);


        //Get Image from Ocr Camera Fragment
        if (getArguments() != null) {
            ittController.setResultUri(getArguments().getParcelable("OcrCameraImage"));
            ittController.startCropImage(ittController.getResultUri());
        }
        // Image Button listener
        mGetImageButton.setOnClickListener((view) -> {
            ittController.openImageSourceUI();
        });
        // Image View listener
        mImageView.setOnClickListener((view) -> {
            if (ittController.getResultUri() != null) {
                ittController.startCropImage(ittController.getResultUri());
            }
            else {
                Toast.makeText(getContext(), "Please choose an image.", Toast.LENGTH_SHORT).show();
            }
        });

        // Speaker Button listener
        mTextToSpeechButton.setOnClickListener((view) -> {
            if (ttsController.getTextToSpeech().isSpeaking() && ttsController.getTextToSpeech() != null) {
                ttsController.getTextToSpeech().stop();
            }
            else if (!mShowText.getText().toString().equals("")) {
                ttsController.speak(mShowText.getText());
                }
            else {
                Toast.makeText(getContext(), "No text to speak.", Toast.LENGTH_SHORT).show();
            }
        });

        // Save Image Button listener
        mSaveImageButton.setOnClickListener((view) -> {
            if (ittController.getResultUri() != null) {
                ittController.saveImageAlertDialog();
            }
            else {
                Toast.makeText(getContext(), "No image to save.", Toast.LENGTH_SHORT).show();
            }
        });

        //Ocr Camera Button. Replace this fragment with Ocr Camera Fragment
        mOcrButton.setOnClickListener((view) -> {
            OcrCameraFragment fragment2 = new OcrCameraFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.landing_menu_container, fragment2);
            fragmentTransaction.addToBackStack(MainOcrFragment.class.getName());
            fragmentTransaction.commit();
        });

        //Back button Listener
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Toast.makeText(getContext(), "Please logout to exit.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        } );

        cameraPermission();
        return rootView;
    }

    //Called when image is cropping.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {//Here change resultCode to requestCode
            ittController.setResultUri(ittController.getPickedImageUri(data));

            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), ittController.getResultUri())) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                ittController.startCropImage(ittController.getResultUri());
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK ) {
                ittController.setResultUri(result.getUri());
                setmImageView(ittController.getResultUri());// show image to image view
                ittController.setBitmapDrawable((BitmapDrawable) mImageView.getDrawable());
                ittController.setBitmap(ittController.getBitmapDrawable().getBitmap());

                StringBuilder stringBuilder = ittController.getTextFromImage(ittController.getBitmap());
                setmShowText(stringBuilder);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception e = result.getError();
                Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Set the TextView mShowText
    private void setmShowText(StringBuilder showText) {
        mShowText.setText(showText.toString());
    }
    //Set the ImageView mImageView
    private void setmImageView(Uri imageUri) {
        if (mImageView.getBackground() != null) {
            mImageView.setBackground(null);
        }
        mImageView.setImageURI(imageUri);
    }

    // Get camera permission
    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, ittController.getRequestCameraPermissionID());
        }
    }

}
