package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crandall_meng_final_project_cecs_453.Controller.LoginController;
import com.example.crandall_meng_final_project_cecs_453.R;

/*
    Displays the password change settings.
 */
public class ChangePasswordActivity extends AppCompatActivity {
    protected LoginController mController;

    protected EditText mOldPass, mNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mController = LoginController.getInstance();

        mOldPass = findViewById(R.id.current_field);
        mNewPass = findViewById(R.id.new_field);

        Button updateButton = findViewById(R.id.update_password_button);
        updateButton.setOnClickListener((view) -> {
            String err = mController.updatePassword(this, mOldPass.getText().toString(), mNewPass.getText().toString());

            if(err != null) {
                Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.update_success_toast), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
