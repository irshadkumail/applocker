package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.ChangeThemeAdapter;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangeThemeFragment extends Fragment {

    private RecyclerView themeRecycler;
    private ChangeThemeAdapter changeThemeAdapter;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_theme_change, parent, false);
        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        themeRecycler= (RecyclerView) rootView.findViewById(R.id.fragment_theme_recycler);
        themeRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        changeThemeAdapter=new ChangeThemeAdapter(getContext());
        themeRecycler.setAdapter(changeThemeAdapter);

    }


}
