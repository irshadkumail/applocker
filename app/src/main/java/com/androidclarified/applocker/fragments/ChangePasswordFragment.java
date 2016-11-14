package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.widgets.LockOverlayView;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangePasswordFragment extends Fragment implements OverlayScreenListener {

    private FrameLayout changePasswordFrame;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle)
    {
        View rootView=layoutInflater.inflate(R.layout.fragment_change_password,parent,false);
        changePasswordFrame=(FrameLayout) rootView.findViewById(R.id.fragment_change_password_frame);
        addPasswordConfirmView();

        return rootView;
    }
    private void addPasswordConfirmView()
    {
        LockOverlayView lockOverlayView=new LockOverlayView(getContext(),true);
        lockOverlayView.setOverlayScreenListener(this);
        changePasswordFrame.addView(lockOverlayView);

    }


    @Override
    public void showOverlayScreen() {

    }

    @Override
    public void hideOverlayScreen() {

    }

    @Override
    public void hideOverlayForCorrectPassword() {

    }
}
