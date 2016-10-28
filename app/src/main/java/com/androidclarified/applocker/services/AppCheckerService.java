package com.androidclarified.applocker.services;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.widgets.LockOverlayView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by My Pc on 10/18/2016.
 */

public class AppCheckerService extends Service implements OverlayScreenListener {

    public String PREVIOUS_PACKAGE_NAME = "";
    public String RUNNING_PACKAGE_NAME = "";
    private Handler handler;
    private AppCheckerClass appCheckerClass;

    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private LockOverlayView lockOverlayView;

    public static boolean isDialogVisile = false;
    public static boolean isPasswordEntered = false;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int startid, int flags) {
        appCheckerClass = new AppCheckerClass();
        handler = new Handler();
        lockOverlayView = new LockOverlayView(this);
        lockOverlayView.setOverlayScreenListener(this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);


        Log.d("Irshad", "Service Started");


        handler.post(appCheckerClass);
        return START_STICKY;
    }

    public String runningAppChecker() {

        String topPackName = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            topPackName = runningAppCheckerForLollipop();
        } else {
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            topPackName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();

        }
        return topPackName;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP | Build.VERSION_CODES.M)
    public String runningAppCheckerForLollipop() {

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - (1000 * 1000), currentTime);

        if (usageStatsList != null && !usageStatsList.isEmpty()) {
            SortedMap<Long, UsageStats> statsSortedMap = new TreeMap<Long, UsageStats>();

            for (UsageStats currentUsageStats : usageStatsList) {
                statsSortedMap.put(currentUsageStats.getLastTimeUsed(), currentUsageStats);
            }
            if (!statsSortedMap.isEmpty()) {
                return statsSortedMap.get(statsSortedMap.lastKey()).getPackageName();
            }

        }

        return " ";
    }

    @Override
    public void showOverlayScreen() {
        windowManager.addView(lockOverlayView, windowParams);
        AppCheckerService.isDialogVisile = true;
    }

    @Override
    public void hideOverlayScreen() {

        windowManager.removeView(lockOverlayView);
        AppCheckerService.isPasswordEntered = false;
        AppCheckerService.isDialogVisile = false;

    }

    @Override
    public void hideOverlayForCorrectPassword() {
        windowManager.removeView(lockOverlayView);
    }

    private class AppCheckerClass implements Runnable {
        @Override
        public void run() {

            if(PREVIOUS_PACKAGE_NAME.isEmpty() && RUNNING_PACKAGE_NAME.isEmpty())
            {
                PREVIOUS_PACKAGE_NAME=runningAppChecker();
                RUNNING_PACKAGE_NAME=PREVIOUS_PACKAGE_NAME;
            }else {
                PREVIOUS_PACKAGE_NAME=RUNNING_PACKAGE_NAME;
                RUNNING_PACKAGE_NAME=runningAppChecker();
            }
            if(!PREVIOUS_PACKAGE_NAME.equalsIgnoreCase(RUNNING_PACKAGE_NAME))
            {
                notifyAppChange();
                Toast.makeText(AppCheckerService.this,"APP CHANGE",Toast.LENGTH_SHORT).show();
            }

            /*
            RUNNING_PACKAGE_NAME = runningAppChecker();

            Toast.makeText(AppCheckerService.this, RUNNING_PACKAGE_NAME, Toast.LENGTH_SHORT).show();
            if (AppSharedPreferences.getAppSharedPreference(AppCheckerService.this, RUNNING_PACKAGE_NAME) && !isDialogVisile) {


                    showOverlayScreen();


            } else if (isDialogVisile && !AppSharedPreferences.getAppSharedPreference(AppCheckerService.this, RUNNING_PACKAGE_NAME)) {

                if (isPasswordEntered) {
                    isPasswordEntered = false;
                    isDialogVisile = false;
                } else {
                    hideOverlayScreen();
                }

            }
            */
            handler.postDelayed(this, 2000);
        }
    }

    private void notifyAppChange()
    {
        if (AppSharedPreferences.getAppSharedPreference(this,RUNNING_PACKAGE_NAME) && !isDialogVisile)
        {
            showOverlayScreen();
        }else if (!AppSharedPreferences.getAppSharedPreference(this,RUNNING_PACKAGE_NAME) && isDialogVisile )
        {
            hideOverlayScreen();
        }


    }


}
