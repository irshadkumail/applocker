package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidclarified.applocker.R;

/**
 * Created by krazybee on 10/27/2016.
 */

public class DrawerFragment extends Fragment {

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle)
    {
        return layoutInflater.inflate(R.layout.fragment_drawer,parent,false);
    }


}
