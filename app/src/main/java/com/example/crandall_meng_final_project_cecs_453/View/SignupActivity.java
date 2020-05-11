package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crandall_meng_final_project_cecs_453.Controller.LoginController;
import com.example.crandall_meng_final_project_cecs_453.R;

/*
    Signup Activity, displays the controls required to attempt to create a new user account.
 */
public class SignupActivity extends AppCompatActivity {
    protected LoginController mController;
    protected EditText mUsername, mPassword, mRetypePassword, mEmail, mPhone, mAge;
    protected Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mController = LoginController.getInstance();
        mUsername = findViewById(R.id.name_field);
        mPassword = findViewById(R.id.password_field);
        mRetypePassword = findViewById(R.id.retype_password_field);
        mEmail = findViewById(R.id.email_field);
        mPhone = findViewById(R.id.phone_field);
        mSignupButton = findViewById(R.id.signup_button);
        mAge = findViewById(R.id.age_field);

        mSignupButton.setOnClickListener((view) -> {
            String err = mController.attemptSignup(this, mUsername.getText().toString(), mPassword.getText().toString(),
                    mRetypePassword.getText().toString(), mEmail.getText().toString(), mPhone.getText().toString(), mAge.getText().toString());

            mPassword.setText("");
            mRetypePassword.setText("");

            if(err != null) {
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
            }

        });

    }


}
