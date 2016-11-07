package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidclarified.applocker.R;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangePasswordFragment extends Fragment {


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle)
    {
        View rootView=layoutInflater.inflate(R.layout.fragment_change_password,parent,false);

        return rootView;
    }

}
