package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.crandall_meng_final_project_cecs_453.Controller.LoginController;
import com.example.crandall_meng_final_project_cecs_453.R;

/*
    Settings fragment. Has various options for account management, About / Help pages, and has a
    logout button.
 */
public class LandingSettingsFragment extends Fragment {
    protected ViewGroup mView;

    protected LoginController mController;

    protected Button mChangePassword;
    protected Button mEditProfile;
    protected Button mHelp;
    protected Button mAbout;
    protected Button mLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (ViewGroup) inflater.inflate(R.layout.activity_landing_settings_fragment, container, false);

        mController = LoginController.getInstance();

        mChangePassword = mView.findViewById(R.id.change_password_button);
        mEditProfile = mView.findViewById(R.id.edit_profile_button);
        mHelp = mView.findViewById(R.id.help_button);
        mAbout = mView.findViewById(R.id.about_button);
        mLogOut = mView.findViewById(R.id.logout_button);

        mChangePassword.setOnClickListener((view) -> startActivity(new Intent(getActivity(), ChangePasswordActivity.class)));
        mEditProfile.setOnClickListener((view) -> startActivity(new Intent(getActivity(), EditProfileActivity.class)));
        mHelp.setOnClickListener((view) -> startActivity(new Intent(getActivity(), HelpActivity.class)));
        mAbout.setOnClickListener((view) -> startActivity(new Intent(getActivity(), AboutActivity.class)));

        mLogOut.setOnClickListener((view) -> {
            mController.logOut(getActivity());
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return mView;
    }
}
