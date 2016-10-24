package com.androidclarified.applocker.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by My Pc on 10/23/2016.
 */

public class AppSharedPreferences {


    public static final String APP_PREFERENCE_FILE_NAME="com.androidclarified.applocker.preferences";


    public static void putAppSharedPreferences(Context context,String keyName,boolean keyValue)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(keyName,keyValue);
        editor.commit();
    }

    public static boolean getAppSharedPreference(Context context,String keyName)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(keyName,false);
    }



}
