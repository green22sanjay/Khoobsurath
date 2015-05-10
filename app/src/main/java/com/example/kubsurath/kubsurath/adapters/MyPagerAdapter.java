package com.example.kubsurath.kubsurath.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip.IconTabProvider;
import com.example.kubsurath.kubsurath.Category;
import com.example.kubsurath.kubsurath.Login;
import com.example.kubsurath.kubsurath.R;
import com.example.kubsurath.kubsurath.latest;
import com.example.kubsurath.kubsurath.trending;
import com.example.kubsurath.kubsurath.winner;

public class MyPagerAdapter extends FragmentPagerAdapter implements IconTabProvider {


    final int PAGE_COUNT = 5;
    private int tabIcons[] = {R.drawable.ic_action_social_whatshot, R.drawable.ic_action_action_trending_up,
            R.drawable.ic_action_winner,R.drawable.ic_action_device_brightness_high,R.drawable.ic_action_action_account_circle};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int item) {

        switch (item) {

            // Open HomeFragment.java
            case 0:
                trending mtrending = new trending();
                return mtrending;
            // Open PlaceOrderFragment.java
            case 1:
                latest mlatest = new latest();
                return mlatest;
            case 2:
                winner mwinner = new winner();
                return mwinner;
            case 3:
                Category mCategory = new Category();
                return mCategory;
            case 4:

                    Login mLogin = new Login();
                    return mLogin;

        }
        return null;
    }

    @Override
    public int getPageIconResId(int position) {

        return tabIcons[position];
    }
}
