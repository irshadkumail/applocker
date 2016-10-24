package com.androidclarified.applocker.services;

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
import android.view.ViewGroup;
import android.view.WindowManager;

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

public class AppCheckerService extends Service {

    public static String RUNNING_PACKAGE_NAME = "";
    private Handler handler;
    private AppCheckerClass appCheckerClass;
    private boolean isDialogVisile;
    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private LockOverlayView lockOverlayView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int startid, int flags) {
        appCheckerClass = new AppCheckerClass();
        handler = new Handler();
        lockOverlayView=new LockOverlayView(this);
        windowManager= (WindowManager) getSystemService(WINDOW_SERVICE);
        windowParams=new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);


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

    private class AppCheckerClass implements Runnable {
        @Override
        public void run() {

            RUNNING_PACKAGE_NAME = runningAppChecker();
            handler.postDelayed(this, 2000);

            if (AppSharedPreferences.getAppSharedPreference(AppCheckerService.this, RUNNING_PACKAGE_NAME) && !isDialogVisile) {
                 showDialog();
                isDialogVisile=true;
            } else if(isDialogVisile){
                hideDialog();
                isDialogVisile=false;
            }
        }
    }
    private void showDialog()
    {
        windowManager.addView(lockOverlayView,windowParams);
    }
    private void hideDialog()
    {
        windowManager.removeView(lockOverlayView);
    }


}
