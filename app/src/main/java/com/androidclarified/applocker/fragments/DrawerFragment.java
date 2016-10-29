package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidclarified.applocker.R;

/**
 * Created by krazybee on 10/27/2016.
 */

public class DrawerFragment extends Fragment {

    private ListView drawerListView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle)
    {
        View rootView= layoutInflater.inflate(R.layout.fragment_drawer,parent,false);



        return rootView;
    }
    private void initViews(View rootView)
    {
        drawerListView= (ListView) rootView.findViewById(R.id.drawer_fragment_list);

    }


}
