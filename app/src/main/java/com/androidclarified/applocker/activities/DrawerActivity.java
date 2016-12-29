package com.androidclarified.applocker.activities;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.fragments.ChangePasswordFragment;
import com.androidclarified.applocker.fragments.ChangeThemeFragment;
import com.androidclarified.applocker.fragments.SettingsPrefFragment;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.utils.AppUtils;

/**
 * Created by My Pc on 11/6/2016.
 */

public class DrawerActivity extends AppCompatActivity implements OverlayScreenListener {

    private FrameLayout drawerFrame;
    private TextView toolbarHeading;
    private Toolbar toolbar;
    private String selectedAction="";

    private Fragment selectedFragment;

    public static final String THEME_CHANGE_ACTION="theme_change_action";
    public static final String PASSWORD_CHANGE_ACTION="password_change_action";
    public static final String SETTING_ACTION="setting_action";


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
        toolbar= (Toolbar) findViewById(R.id.drawer_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    private Fragment getSelectedFragment()
    {

        switch (selectedAction)
        {
            case THEME_CHANGE_ACTION:
                getSupportActionBar().setTitle("Change Theme");
                selectedFragment=new ChangeThemeFragment();
                break;
            case PASSWORD_CHANGE_ACTION:
                getSupportActionBar().setTitle("Change Password");
                selectedFragment=new ChangePasswordFragment();
                break;
            case SETTING_ACTION:
                getSupportActionBar().setTitle("Settings");
                selectedFragment=new SettingsPrefFragment();
                break;

        }
        return selectedFragment;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void addSelectedFragment()
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_activity_frame,getSelectedFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void showOverlayScreen() {
        ((ChangePasswordFragment)selectedFragment).showOverlay();

    }

    @Override
    public void hideOverlayScreen() {
        ((ChangePasswordFragment)selectedFragment).hideOverlay();
        super.onBackPressed();


    }
}
