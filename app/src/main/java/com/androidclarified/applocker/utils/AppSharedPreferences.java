package com.androidclarified.applocker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static com.androidclarified.applocker.utils.AppConstants.APP_PREFERENCE_FILE_NAME;
import static com.androidclarified.applocker.utils.AppConstants.GALLERY_IMAGE;
import static com.androidclarified.applocker.utils.AppConstants.IS_SERVICE_RUNNING;
import static com.androidclarified.applocker.utils.AppConstants.USER_INFO_EMPTY;
import static com.androidclarified.applocker.utils.AppConstants.USER_INFO_KEY;

/**
 * Created by My Pc on 10/23/2016.
 */

public class AppSharedPreferences {



    public static void putUserInfoPreference(Context context,String userInfo)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(USER_INFO_KEY,userInfo);
        editor.commit();

    }

    public static String getUserInfoPreferences(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_INFO_KEY,USER_INFO_EMPTY);


    }

    public static void putGalleryImagePreferences(Context context,String imagePath)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AppConstants.GALLERY_IMAGE,imagePath);
        editor.commit();
    }
    public static String getGalleryImagePreference(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(GALLERY_IMAGE,"");
    }

    public static void putAppSharedPreferences(Context context,String keyName,boolean keyValue)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        Log.d("Irshad","Changing Preference "+keyName+" Value "+keyValue);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(keyName,keyValue);
        editor.commit();
    }

    public static void putPasswordSharedPreference(Context context,String password)
    {

        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AppConstants.PASSWORD_KEY,password);
        editor.commit();
    }
    public static String getPasswordPreference(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstants.PASSWORD_KEY,"0000");
    }

    public static boolean getAppSharedPreference(Context context,String keyName)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(keyName,false);
    }

    public static void putLockThemePreference(Context context,int themeIndex)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(AppConstants.LOCK_THEME_KEY,themeIndex);
        editor.commit();
    }

    public static int getLockThemePreference(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(AppConstants.LOCK_THEME_KEY,0);
    }

    public static void putFirstTimePreference(Context context,boolean isFirst)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(AppConstants.FIRST_TIME_USER,isFirst);
        editor.commit();

    }
    public static boolean getFirstTimePreference(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(AppConstants.FIRST_TIME_USER,false);
    }

    public static void putServiceRunningPreference(Context context,boolean isRunning){
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(IS_SERVICE_RUNNING,isRunning);
        editor.commit();

    }
    public static boolean getServiceRunningPreferencec(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(APP_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_SERVICE_RUNNING,false);

    }




}
