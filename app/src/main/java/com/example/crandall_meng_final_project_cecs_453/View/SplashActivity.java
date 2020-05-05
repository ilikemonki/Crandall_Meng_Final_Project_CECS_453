package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.crandall_meng_final_project_cecs_453.R;

public class SplashActivity extends AppCompatActivity {
    protected int mTimeoutMiliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mTimeoutMiliseconds = getResources().getInteger(R.integer.splash_duration_miliseconds);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LoginActivity.class));
        }, mTimeoutMiliseconds);
    }
}
