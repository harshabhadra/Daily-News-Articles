package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimplePagerAdapter extends FragmentPagerAdapter {

    private String tabtiles [] = {"HeadLine", "Sports", "Mobile and Gadgets","Technology","Politics", "Entertainment"};
    private Context context;
    public SimplePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new TimeLineFragment();
        } else if (position == 1) {
            return new SportsFragment();
        } else if (position == 2) {
            return new MobileFragment();
        }else if (position ==3){
            return new TechnologyFragment();
        }else if (position ==4){
            return new PoliticsFragment();
        }else {
            return new EntertainmentFragment();
        }
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabtiles[position];
    }
}
