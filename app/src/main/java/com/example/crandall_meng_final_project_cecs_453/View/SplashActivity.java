package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.crandall_meng_final_project_cecs_453.Controller.LoginController;
import com.example.crandall_meng_final_project_cecs_453.R;

/*
    Splash screen, also checks for a default login. If a default login is found transitions to
    the Landing activity, otherwise transitions to the login page.
 */
public class SplashActivity extends AppCompatActivity {
    protected LoginController mController;
    protected int mTimeoutMiliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mController = LoginController.getInstance();
        mTimeoutMiliseconds = getResources().getInteger(R.integer.splash_duration_miliseconds);

        Class firstActivity;
        if(mController.checkForDefaultLogin(this)) { firstActivity = LandingActivity.class; }
        else { firstActivity = LoginActivity.class; }

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, firstActivity));
        }, mTimeoutMiliseconds);
    }
}
