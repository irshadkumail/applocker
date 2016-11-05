package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.activities.MainActivity;
import com.androidclarified.applocker.adapters.AppListAdapter;
import com.androidclarified.applocker.listeners.OnAppCheckedListener;
import com.androidclarified.applocker.listeners.OnRecieveAppCheckedListener;
import com.androidclarified.applocker.model.AppBean;

import java.util.ArrayList;

/**
 * Created by krazybee on 10/27/2016.
 */

public class InstalledAppsFragment extends Fragment implements OnRecieveAppCheckedListener {


    private static final String INSTALLED_APPS_LIST="installed_apps_list";
    private RecyclerView installedAppsRecycler;
    private AppListAdapter appListAdapter;
    private ArrayList<AppBean> appBeanList;



    public static InstalledAppsFragment newInstance(ArrayList<AppBean> installedAppsList)
    {
        InstalledAppsFragment installedAppsFragment=new InstalledAppsFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList(INSTALLED_APPS_LIST,installedAppsList);
        installedAppsFragment.setArguments(bundle);

        return installedAppsFragment;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        if(getArguments()!=null)
        {
         appBeanList=getArguments().getParcelableArrayList(INSTALLED_APPS_LIST);

        }
    }

    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);

    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle)
    {
        View rootView= layoutInflater.inflate(R.layout.fragment_installed_apps,parent,false);
        init(rootView);


        return rootView;
    }

    public void init(View rootView)
    {
        installedAppsRecycler= (RecyclerView) rootView.findViewById(R.id.fragment_installed_recycler);
        installedAppsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        appListAdapter=new AppListAdapter(getContext(),appBeanList);
        installedAppsRecycler.setAdapter(appListAdapter);

    }


    @Override
    public void onAppCheckedReceived(String packageName) {

        final int index=findPackageIndex(packageName);
        Handler handler = new Handler();
        Log.d("Irshad","InstalledAppsFragment packname"+packageName);

        final Runnable r = new Runnable() {
            public void run() {
                appListAdapter.notifyDataSetChanged();
            }
        };

        handler.post(r);


    }
    private int findPackageIndex(String packageName)
    {
        for (int i=0;i<appBeanList.size();i++)
        {
            if(appBeanList.get(i).getPackName().equals(packageName))
                return i;
        }
        return -1;
    }
}
