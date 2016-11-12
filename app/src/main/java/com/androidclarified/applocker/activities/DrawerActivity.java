package com.androidclarified.applocker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.fragments.ChangePasswordFragment;
import com.androidclarified.applocker.fragments.ChangeThemeFragment;
import com.androidclarified.applocker.utils.AppUtils;

/**
 * Created by My Pc on 11/6/2016.
 */

public class DrawerActivity extends AppCompatActivity {

    private FrameLayout drawerFrame;
    private TextView toolbarHeading;
    private String selectedAction="";

    public static final String THEME_CHANGE_ACTION="theme_change_action";
    public static final String PASSWORD_CHANGE_ACTION="password_change_action";


    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_drawer);
        selectedAction=getIntent().getAction();
        initViews();
        addSelectedFragment();
    }
    private void initViews()
    {
        drawerFrame= (FrameLayout) findViewById(R.id.drawer_activity_frame);
        toolbarHeading= (TextView) findViewById(R.id.drawer_activity_heading);

    }
    private Fragment getSelectedFragment()
    {
        Fragment selectedFragment=null;
        switch (selectedAction)
        {
            case THEME_CHANGE_ACTION:
                selectedFragment=new ChangeThemeFragment();
                toolbarHeading.setText("Change Theme");
                toolbarHeading.setTypeface(AppUtils.getPrimaryTextTypeface(this));
                break;
            case PASSWORD_CHANGE_ACTION:
                selectedFragment=new ChangePasswordFragment();
                toolbarHeading.setText("Change Password");
                toolbarHeading.setTypeface(AppUtils.getPrimaryTextTypeface(this));
                break;

        }
        return selectedFragment;
    }
    private void addSelectedFragment()
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_activity_frame,getSelectedFragment());
        fragmentTransaction.commit();
    }




}