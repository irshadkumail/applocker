package com.androidclarified.applocker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.widgets.LockOverlayView;

/**
 * Created by My Pc on 11/6/2016.
 */

public class ChangePasswordFragment extends Fragment {

    private FrameLayout changePasswordFrame;
    LockOverlayView lockOverlayView;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_change_password, parent, false);
        changePasswordFrame = (FrameLayout) rootView.findViewById(R.id.fragment_change_password_frame);
        init();


        return rootView;
    }

    public void init() {
        lockOverlayView = new LockOverlayView(getContext(), true);
        showOverlay();
    }


    public void showOverlay() {
        if (getActivity() != null) {
            lockOverlayView.setOverlayScreenListener((OverlayScreenListener) getActivity());
            changePasswordFrame.addView(lockOverlayView);
        }
    }


    public void hideOverlay() {
        changePasswordFrame.removeView(lockOverlayView);
        Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_SHORT).show();
    }


}
