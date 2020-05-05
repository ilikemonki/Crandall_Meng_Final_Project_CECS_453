package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.crandall_meng_final_project_cecs_453.R;


public class LandingMenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_landing_menu_fragment, container, false);

        MainOcrFragment newFragment = new MainOcrFragment();
        //FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.landing_menu_container, newFragment);
        //fragmentTransaction.addToBackStack(LandingMenuFragment.class.getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        return rootView;
    }

}
