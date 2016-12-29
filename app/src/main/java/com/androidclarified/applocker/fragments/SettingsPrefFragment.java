package com.androidclarified.applocker.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.CheckBox;

import com.androidclarified.applocker.R;

/**
 * Created by krazybee on 12/28/2016.
 */

public class SettingsPrefFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {


    private CheckBoxPreference hidePasswordPref, showNotiPref;

    private PreferenceScreen packageUsagePref, drawOverPref;

    private SharedPreferences sharedPreferences;


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_frag);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());

        hidePasswordPref = (CheckBoxPreference) getPreferenceScreen().findPreference("show_password_pref");
        showNotiPref = (CheckBoxPreference) getPreferenceScreen().findPreference("hide_notification_pref");
        packageUsagePref = (PreferenceScreen) getPreferenceScreen().findPreference("package_usage_pref");
        drawOverPref = (PreferenceScreen) getPreferenceScreen().findPreference("draw_over_pref");

        if (!isMarshmallow())
            drawOverPref.setVisible(false);

        hidePasswordPref.setOnPreferenceChangeListener(this);
        showNotiPref.setOnPreferenceChangeListener(this);
        packageUsagePref.setOnPreferenceClickListener(this);
        drawOverPref.setOnPreferenceClickListener(this);
    }

    private boolean isMarshmallow()
    {
        return (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        switch (preference.getKey())
        {
            case "show_password_pref":
                Log.d("Irshad",sharedPreferences.getBoolean("show_password_key",false)+"");
                break;
            case "hide_notification_pref":
                Log.d("Irshad",sharedPreferences.getBoolean("hide_notification_key",false)+"");
                break;

        }


        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        switch (preference.getKey())
        {
            case "package_usage_key":
                Intent actionUsageIntent=new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                if (actionUsageIntent.resolveActivity(getContext().getPackageManager())!=null)
                    startActivity(actionUsageIntent);
                break;
            case "draw_over_key":
                Intent drawOverlayIntent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                if (drawOverlayIntent.resolveActivity(getContext().getPackageManager())!=null)
                    startActivity(drawOverlayIntent);
                break;
        }
        return true;
    }
}
