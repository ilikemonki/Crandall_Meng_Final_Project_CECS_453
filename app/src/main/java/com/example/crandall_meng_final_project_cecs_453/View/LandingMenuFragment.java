package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.crandall_meng_final_project_cecs_453.R;

/*
    Menu fragment displayed in the Settings option. Begins the core of the application after
    account book keeping.
    LandingMenuFragment is a host fragment that will be replaced with other fragments.
 */
public class LandingMenuFragment extends Fragment {

    //Replace this fragment with MainOcrFragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_landing_menu_fragment, container, false);

        MainOcrFragment newFragment = new MainOcrFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.landing_menu_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        return rootView;
    }


}
