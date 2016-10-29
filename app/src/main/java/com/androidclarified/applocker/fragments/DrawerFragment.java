package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.DrawerListAdapter;

/**
 * Created by krazybee on 10/27/2016.
 */

public class DrawerFragment extends Fragment {

    private ListView drawerListView;
    private DrawerListAdapter drawerListAdapter;
    private String[] drawerStrings;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle)
    {
        View rootView= layoutInflater.inflate(R.layout.fragment_drawer,parent,false);
        init(rootView);


        return rootView;
    }
    private void init(View rootView)
    {
        drawerStrings=getContext().getResources().getStringArray(R.array.drawer_list_array);
        drawerListView= (ListView) rootView.findViewById(R.id.drawer_fragment_list);
        drawerListAdapter=new DrawerListAdapter(getContext(),drawerStrings);
        drawerListView.setAdapter(drawerListAdapter);

    }


}
