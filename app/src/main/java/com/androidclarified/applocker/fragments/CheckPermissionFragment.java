package com.androidclarified.applocker.fragments;

import android.app.AppOpsManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.utils.AppUtils;

/**
 * Created by krazybee on 11/22/2016.
 */

public class CheckPermissionFragment extends Fragment implements View.OnClickListener {

    RelativeLayout grantUsageLayout, drawOverLayout;
    CheckBox grantUsageCheckbox, drawOverCheckbox;


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_check_permission, parent, false);
        init(rootView);


        return rootView;
    }

    private void init(View rootView) {
        grantUsageLayout = (RelativeLayout) rootView.findViewById(R.id.frag_check_perm_usage);
        drawOverLayout = (RelativeLayout) rootView.findViewById(R.id.frag_check_perm_draw);
        grantUsageCheckbox = (CheckBox) rootView.findViewById(R.id.frag_check_perm_usage_checkbox);
        drawOverCheckbox = (CheckBox) rootView.findViewById(R.id.frag_check_perm_draw_checkbox);
        grantUsageLayout.setOnClickListener(this);
        drawOverLayout.setOnClickListener(this);

    }

    public void onResume()
    {
        super.onResume();
        checkForPermission();


    }



    private void checkForPermission() {

        if (Build.VERSION.SDK_INT == 23) {
            drawOverLayout.setVisibility(View.VISIBLE);
            if (AppUtils.canDrawOverlay(getContext()))
                drawOverCheckbox.setChecked(true);
            else
                drawOverCheckbox.setChecked(false);


        }
        if (AppUtils.isUsagePermissionGranted(getContext()))
            grantUsageCheckbox.setChecked(true);
        else
            grantUsageCheckbox.setChecked(false);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.frag_check_perm_usage:
               Intent usageIntent=new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                if (usageIntent.resolveActivity(getContext().getPackageManager())!=null)
                    startActivity(usageIntent);
                break;
            case R.id.frag_check_perm_draw:
                Intent overlayIntent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                if (overlayIntent.resolveActivity(getContext().getPackageManager())!=null)
                    startActivity(overlayIntent);
                break;

        }



    }
}
