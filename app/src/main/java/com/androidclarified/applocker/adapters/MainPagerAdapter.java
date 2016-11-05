package com.androidclarified.applocker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidclarified.applocker.activities.MainActivity;
import com.androidclarified.applocker.fragments.AllAppsFragment;
import com.androidclarified.applocker.fragments.InstalledAppsFragment;
import com.androidclarified.applocker.listeners.OnRecieveAppCheckedListener;
import com.androidclarified.applocker.model.AppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krazybee on 10/27/2016.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {


    private ArrayList<AppBean> nonSystemApps;
    private ArrayList<AppBean> allApps;
    private MainActivity mainActivity;

    public MainPagerAdapter(FragmentManager fragmentManager, ArrayList<AppBean> nonSystemApps, ArrayList<AppBean> allApps, MainActivity mainActivity) {
        super(fragmentManager);

        this.nonSystemApps = nonSystemApps;
        this.allApps = allApps;
        this.mainActivity=mainActivity;


    }

    public Fragment getItem(int pos) {
        Fragment fragment = null;

        switch (pos) {
            case 0:
                fragment = InstalledAppsFragment.newInstance(nonSystemApps);
                mainActivity.registerRecieveAppCheckedListeners((OnRecieveAppCheckedListener) fragment);
                break;
            case 1:
                fragment = AllAppsFragment.newInstance(allApps);
                mainActivity.registerRecieveAppCheckedListeners((OnRecieveAppCheckedListener) fragment);
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
