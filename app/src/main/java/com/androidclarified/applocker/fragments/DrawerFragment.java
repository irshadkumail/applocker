package com.androidclarified.applocker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.activities.DrawerActivity;
import com.androidclarified.applocker.adapters.DrawerListAdapter;
import com.androidclarified.applocker.listeners.OnDrawerItemClick;

/**
 * Created by krazybee on 10/27/2016.
 */

public class DrawerFragment extends Fragment implements OnDrawerItemClick {

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
        drawerListAdapter=new DrawerListAdapter(getContext(),drawerStrings,this);
        drawerListView.setAdapter(drawerListAdapter);

    }


    @Override
    public void onItemClicked(int type) {
        Intent intent=new Intent(getActivity(), DrawerActivity.class);
        switch (type)
        {
            case 0:
                intent.setAction(DrawerActivity.THEME_CHANGE_ACTION);
                startActivity(intent);
                break;
            case 1:
                intent.setAction(DrawerActivity.PASSWORD_CHANGE_ACTION);
                startActivity(intent);
                break;
        }

    }
}
