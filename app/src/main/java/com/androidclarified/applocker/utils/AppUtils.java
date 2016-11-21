package com.androidclarified.applocker.utils;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;

import com.androidclarified.applocker.R;

import static com.androidclarified.applocker.utils.AppConstants.BLUE_GREY_THEME;
import static com.androidclarified.applocker.utils.AppConstants.CHATIT_BLUE_THEME;
import static com.androidclarified.applocker.utils.AppConstants.CYAN_THEME;
import static com.androidclarified.applocker.utils.AppConstants.DEEP_ORANGE_THEME;
import static com.androidclarified.applocker.utils.AppConstants.HOME_BLACK_THEME;
import static com.androidclarified.applocker.utils.AppConstants.HOME_BLUE_THEME;
import static com.androidclarified.applocker.utils.AppConstants.JET_BLACK_THEME;
import static com.androidclarified.applocker.utils.AppConstants.TEAL_THEME;
import static com.androidclarified.applocker.utils.AppConstants.TRANSPARANT_GREY_THEME;

/**
 * Created by krazybee on 11/11/2016.
 */

public class AppUtils {


    public static Typeface getPrimaryTextTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/MontserratRegular.ttf");
    }

    public static Typeface getFancyTextTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/QueenofHeaven.ttf");
    }

    public static Typeface getSecondaryTextTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/PoppinsRegular.ttf");
    }

    public static int getColor(Context context, int index) {
        int selected = context.getResources().getColor(R.color.jetblack);
        switch (index) {
            case JET_BLACK_THEME:
                selected = context.getResources().getColor(R.color.jetblack);
                break;
            case TEAL_THEME:
                selected = context.getResources().getColor(R.color.teal);
                break;
            case CYAN_THEME:
                selected = context.getResources().getColor(R.color.cyan);
                break;
            case BLUE_GREY_THEME:
                selected = context.getResources().getColor(R.color.blue_grey);
                break;
            case CHATIT_BLUE_THEME:
                selected = context.getResources().getColor(R.color.chatitemblue);
                break;
            case HOME_BLACK_THEME:
                selected = context.getResources().getColor(R.color.home_intro_color);
                break;
            case HOME_BLUE_THEME:
                selected = context.getResources().getColor(R.color.light_green);
                break;
            case DEEP_ORANGE_THEME:
                selected = context.getResources().getColor(R.color.deep_orange);
                break;
            case TRANSPARANT_GREY_THEME:
                selected=context.getResources().getColor(R.color.transparent_grey);
                break;


        }
        return selected;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP | Build.VERSION_CODES.M)
    public static boolean isUsagePermissionGranted(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
            return (mode == AppOpsManager.MODE_ALLOWED);
        } else
            return true;
    }

    public static boolean canDrawOverlay(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return Settings.canDrawOverlays(context);
        else
            return true;

    }

}
