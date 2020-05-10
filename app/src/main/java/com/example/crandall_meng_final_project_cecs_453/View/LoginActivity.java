package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crandall_meng_final_project_cecs_453.R;

import com.example.crandall_meng_final_project_cecs_453.Controller.LoginController;

/*
    Concerns:
        "some fields require a default value" -- are hints okay ?

        use android resource strings for all error messages in controller / Model ?
        using scroll bars of neccesary for signup page ?
        comment code ?

        Check phone number validity carefully ?

        worry about synchronizing access to Database ?

        customize page transition animation ?

        add volume to settings ?
 */
public class LoginActivity extends AppCompatActivity {
    protected LoginController mController;
    protected EditText mUsername, mPassword;
    protected Button mSignupButton, mLoginButton;
    protected CheckBox mRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mController = LoginController.getInstance();
        mUsername = findViewById(R.id.name_field);
        mPassword = findViewById(R.id.password_field);
        mSignupButton = findViewById(R.id.signup_button);
        mLoginButton = findViewById(R.id.login_button);
        mRememberMe = findViewById(R.id.remember_checkbox);

        /*
        //FOR DEBUGGING PURPOSES ONLY, DELETE FROM HERE
        mController.attemptSignup(this, "p", "pppppppp",
                "pppppppp", "p@y.com", "1231231234", "30");
        mUsername.setText("p");
        mPassword.setText("pppppppp");
        //TO HERE
        */

        mSignupButton.setOnClickListener((view) -> {
            startActivity(new Intent(this, SignupActivity.class));
        });

        mLoginButton.setOnClickListener((view) -> {
            String err = mController.attemptLogin(this, mUsername.getText().toString(), mPassword.getText().toString(), mRememberMe.isChecked());

            mPassword.setText("");

            if(err != null) {
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
            else {
                startActivity(new Intent(this, LandingActivity.class));
            }
        });



    }



}
