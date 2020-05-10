package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crandall_meng_final_project_cecs_453.Controller.LoginController;
import com.example.crandall_meng_final_project_cecs_453.R;

public class EditProfileActivity extends AppCompatActivity {
    protected LoginController mController;

    EditText mUsername, mPassword, mEmail, mPhone, mAge;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mController = LoginController.getInstance();


        mUsername = findViewById(R.id.edit_profile_username);
        mPassword = findViewById(R.id.edit_profile_password);
        mEmail = findViewById(R.id.edit_profile_email);
        mPhone = findViewById(R.id.edit_profile_phone);
        mAge = findViewById(R.id.edit_profile_age);
        mButton = findViewById(R.id.edit_profile_button);

        mUsername.setText(mController.getUsername());
        mEmail.setText(mController.getEmail());
        mPhone.setText(mController.getPhone());
        mAge.setText(String.valueOf(mController.getAge()));

        mButton.setOnClickListener((view) -> {
            String err = mController.updateProfile(this,
                    mUsername.getText().toString(),
                    mPassword.getText().toString(),
                    mEmail.getText().toString(),
                    mPhone.getText().toString(),
                    mAge.getText().toString()
            );

            if(err != null) {
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.edit_success_toast), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
