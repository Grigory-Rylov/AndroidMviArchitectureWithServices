package com.grishberg.servicetest.presentation.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grishberg.servicetest.presentation.ui.fragments.PageWithDateFragment;
import com.grishberg.servicetest.presentation.ui.fragments.PagerFragment;

/**
 * Created by grishberg on 01.04.17.
 */

public class MainScreenPageAdapter extends FragmentPagerAdapter {
    private static final int PAGES_COUNT = 10;

    public MainScreenPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return PageWithDateFragment.newInstance();
        }
        return PagerFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGES_COUNT;
    }
}
