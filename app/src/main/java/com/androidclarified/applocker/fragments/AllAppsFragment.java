package com.androidclarified.applocker.fragments;

import android.app.Activity;
import android.os.Bundle;
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
import java.util.List;

/**
 * Created by krazybee on 10/27/2016.
 */

public class AllAppsFragment extends Fragment implements OnAppCheckedListener,OnRecieveAppCheckedListener {

    private static final String ALL_APPS_LIST = "all_apps_list";

    ArrayList<AppBean> allAppsList;
    private RecyclerView allAppsRecycler;
    private AppListAdapter appListAdapter;
    private MainActivity mainActivity;


    public static AllAppsFragment newInstance(ArrayList<AppBean> allAppsList) {
        AllAppsFragment allAppsFragment = new AllAppsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ALL_APPS_LIST, allAppsList);
        allAppsFragment.setArguments(bundle);


        return allAppsFragment;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        if(getArguments()!=null)
        {
            allAppsList=getArguments().getParcelableArrayList(ALL_APPS_LIST);


        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_all_apps, parent, false);
        init(rootView);

        return rootView;
    }
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);
        mainActivity= (MainActivity) getActivity();
        mainActivity.registerRecieveAppCheckedListeners(this);

    }

    public void init(View rootView)
    {
        allAppsRecycler= (RecyclerView) rootView.findViewById(R.id.fragment_all_recycler);
        allAppsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        appListAdapter=new AppListAdapter(getContext(),allAppsList);
        allAppsRecycler.setAdapter(appListAdapter);
    }


    @Override
    public void onAppChecked(String packageName) {

        mainActivity.onAppChecked(packageName);
    }

    @Override
    public void onAppCheckedReceived(String packageName) {
        Log.d("Irshad","AllAppsChanged");

    }
    pur
}
