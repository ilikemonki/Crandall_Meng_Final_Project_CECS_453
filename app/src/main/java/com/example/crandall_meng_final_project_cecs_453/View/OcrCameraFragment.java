package com.example.crandall_meng_final_project_cecs_453.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crandall_meng_final_project_cecs_453.Controller.OcrCameraController;
import com.example.crandall_meng_final_project_cecs_453.R;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import java.io.IOException;

// Real-time camera scanning fragment.
// Controls the camera, prints text to screen, captures image, saves image, and send image back to OcrMainFragment.
public class OcrCameraFragment extends Fragment {
    private SurfaceView mCameraView;
    private TextView mTextView;
    private ImageButton mCameraButton;
    private Button mYesButton;
    private Button mNoButton;
    private TextView mSaveTextView;

    private OcrCameraController cameraController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_ocr_camera, container, false);

        cameraController = new OcrCameraController(getContext());
        mCameraView = rootView.findViewById(R.id.surface_view);
        mTextView = rootView.findViewById(R.id.text_view);
        mCameraButton = rootView.findViewById(R.id.btn_camera);
        mYesButton = rootView.findViewById(R.id.btn_yes);
        mNoButton = rootView.findViewById(R.id.btn_no);
        mSaveTextView = rootView.findViewById(R.id.save_textView);

        hidePopup();

        //Camera Button
        mCameraButton.setOnClickListener((view) -> {
            cameraController.takePicture();
            showPopup();
        });
        //Popup Yes Button
        mYesButton.setOnClickListener((view) -> {
            if (cameraController.getImageBytes() != null) {
                Toast.makeText(getContext(), "Saving Image", Toast.LENGTH_SHORT).show();

                cameraController.saveImage(cameraController.getImageBytes());
                sendBackToMainOcrFragment();
            }
        });
        //Popup No Button
        mNoButton.setOnClickListener((view) -> {
            hidePopup();
            cameraController.startCamera(mCameraView);
        });

        //Setup camera with text recognition
        cameraController.setupOcrCamera();

        //set the surfaceview with camerasource and check for permission
        mCameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                cameraController.getRequestCameraPermissionID());
                        return;
                    }
                    cameraController.getCameraSource().start(mCameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getWriteToExternalPermission();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }


            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraController.getCameraSource().stop();
            }
        });

        //when Text Recognizer gets detection, get the text and print them to screen
        cameraController.getTextRecognizer().setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> textBlockSparseArray = detections.getDetectedItems();
                if (textBlockSparseArray.size() != 0) {
                    mTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < textBlockSparseArray.size(); i++) {
                                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                                stringBuilder.append(textBlock.getValue());
                                stringBuilder.append("\n");
                            }
                            mTextView.setText(stringBuilder.toString());
                        }
                    });
                }
            }
        });

        return rootView;
    }

    //Get permission to write to external storage
    public void getWriteToExternalPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            if (shouldShowRequestPermissionRationale( Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getContext(), "Write to External Storage Denied", Toast.LENGTH_SHORT).show();
            } else {
                //Request the permission
                requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        cameraController.getRequestCameraPermissionID());
            }
        }
    }

    //Hide saved popup
    public void hidePopup() {
        mYesButton.setVisibility(View.INVISIBLE);
        mYesButton.setClickable(false);
        mNoButton.setVisibility(View.INVISIBLE);
        mNoButton.setClickable(false);
        mSaveTextView.setVisibility(View.INVISIBLE);
    }

    //Show saved popup
    public void showPopup() {
        mYesButton.setVisibility(View.VISIBLE);
        mYesButton.setClickable(true);
        mNoButton.setVisibility(View.VISIBLE);
        mNoButton.setClickable(true);
        mSaveTextView.setVisibility(View.VISIBLE);
    }

    //Return to MainOcrFragment with Uri image
    public void sendBackToMainOcrFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("OcrCameraImage", cameraController.getResultUri());
        MainOcrFragment fragobj = new MainOcrFragment();
        fragobj.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.landing_menu_container, fragobj);
        transaction.commit();
    }

}
