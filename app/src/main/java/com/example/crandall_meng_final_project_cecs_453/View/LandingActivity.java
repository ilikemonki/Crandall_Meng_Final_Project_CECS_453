package com.example.crandall_meng_final_project_cecs_453.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.crandall_meng_final_project_cecs_453.R;

/*
    First activity displayed after login. Contains a slider with menu options, and settings options.
 */
public class LandingActivity extends FragmentActivity {
    protected ViewPager mViewPager;
    protected PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mViewPager = findViewById(R.id.view_pager);
        mPagerAdapter = new LandingPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mPagerAdapter);
    }

    // This allows the back button to swap back to the menu from the settings page, rather than
    // defaulting to return to the login page.
    @Override
    public void onBackPressed() {
        int currentItem = mViewPager.getCurrentItem();

        if(currentItem == 0) {
            super.onBackPressed();
        }
        else {
            mViewPager.setCurrentItem(currentItem - 1);
        }
    }

    // Data adapter for the sliding fragments, has Menu and Settings fragments.
    private class LandingPagerAdapter extends FragmentStatePagerAdapter {
        protected static final int NUMBER_PAGES = 2;

        protected Context mCtx;

        public LandingPagerAdapter(FragmentManager fragmentManager, Context ctx) {
            super(fragmentManager);
            mCtx = ctx;
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? new LandingMenuFragment() : new LandingSettingsFragment();
        }

        @Override
        public int getCount() { return NUMBER_PAGES; }


        @Override
        public CharSequence getPageTitle(int position) {
            return mCtx.getResources().getString(position == 0 ? R.string.menu_title : R.string.settings_title);
        }

    }
}
