package com.androidclarified.applocker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.activities.MainActivity;
import com.androidclarified.applocker.utils.AppUtils;

/**
 * Created by krazybee on 11/16/2016.
 */

public class PermissionFragment extends Fragment implements View.OnClickListener {


    Button usagePermBtn, drawPermBtn;


    public static final String PERMISSION_FRAG_TAG = "permission_frag_tag";


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_permission, parent, false);
        initViews(rootView);


        return rootView;
    }


    private void initViews(View rootView) {
        usagePermBtn = (Button) rootView.findViewById(R.id.fragment_perm_usage_btn);
        drawPermBtn = (Button) rootView.findViewById(R.id.fragment_perm_draw_btn);
        usagePermBtn.setOnClickListener(this);
        drawPermBtn.setOnClickListener(this);

    }

    public void onResume()
    {
        super.onResume();
        checkForButtons();
    }

    private void checkForButtons() {
        if (AppUtils.isUsagePermissionGranted(getContext()))
            usagePermBtn.setVisibility(View.GONE);

        if (AppUtils.canDrawOverlay(getContext()))
            drawPermBtn.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_perm_usage_btn:
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                if (intent.resolveActivity(getContext().getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(getContext(), "Do it yourself!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_perm_draw_btn:
                Intent overlayIntent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                if (overlayIntent.resolveActivity(getContext().getPackageManager())!=null)
                    startActivity(overlayIntent);
                break;

        }

    }
}
