package com.androidclarified.applocker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidclarified.applocker.fragments.AllAppsFragment;
import com.androidclarified.applocker.fragments.InstalledAppsFragment;
import com.androidclarified.applocker.model.AppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krazybee on 10/27/2016.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    private ArrayList<AppBean> nonSystemApps;
    private ArrayList<AppBean> allApps;

    public MainPagerAdapter(FragmentManager fragmentManager, ArrayList<AppBean> nonSystemApps, ArrayList<AppBean> allApps) {
        super(fragmentManager);

        this.nonSystemApps = nonSystemApps;
        this.allApps = allApps;
    }

    public Fragment getItem(int pos) {
        Fragment fragment = null;

        switch (pos) {
            case 0:
                fragment = InstalledAppsFragment.newInstance(nonSystemApps);
                break;
            case 1:
                fragment = AllAppsFragment.newInstance(allApps);
                break;

        }

        return fragment;
    }

    public int getCount()
    {
        return 2;
    }

    public CharSequence getPageTitle(int pos)
    {
        String title="";
        switch (pos)
        {
            case 0:
                title="INSTALLED";
                break;
            case 1:
                title="ALL";
                break;

        }
        return title;
    }


}
