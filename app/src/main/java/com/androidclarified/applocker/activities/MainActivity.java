package com.androidclarified.applocker.activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.MainPagerAdapter;
import com.androidclarified.applocker.fragments.PermissionFragment;
import com.androidclarified.applocker.listeners.OnAppCheckedListener;
import com.androidclarified.applocker.listeners.OnRecieveAppCheckedListener;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.services.AppCheckerService;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnAppCheckedListener, NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView toolbarHeading;
    private FrameLayout versionFrame;
    private MainPagerAdapter mainPagerAdapter;
    private ArrayList<AppBean> allAppsList;
    private ArrayList<AppBean> installedAppsList;
    private ArrayList<AppBean> appBeanList;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private PermissionFragment permissionFragment;
    private ArrayList<OnRecieveAppCheckedListener> onRecieveAppCheckedListeners;
    public PendingIntent pendingIntent;
    public AlarmManager alarmManager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBeanList = getIntent().getExtras().getParcelableArrayList(SplashActivity.APP_BEAN_LIST);
        createSeparateLists(appBeanList);
        initViews();



    }

    public void onResume()
    {
        super.onResume();
        initPermission();

    }

    private void initPermission() {

        if (!AppUtils.isUsagePermissionGranted(this) || !AppUtils.canDrawOverlay(this)) {
            addFragment(permissionFragment);
        } else {
            removeFragment(permissionFragment);
            startCheckingforApps();
        }

    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_frame, fragment);
        fragmentTransaction.addToBackStack(PermissionFragment.PERMISSION_FRAG_TAG);
        fragmentTransaction.commit();

    }
    public void removeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean checkForPermission() {
        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
        int mode = appOpsManager.checkOp(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        return (mode == AppOpsManager.MODE_ALLOWED);
    }

    private void startCheckingforApps() {
        startService(new Intent(MainActivity.this, AppCheckerService.class));
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),(1000)*(60)*(10),pendingIntent);
    }

    private void initViews() {

        //Views Instantiation

        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        permissionFragment=new PermissionFragment();
        navigationView = (NavigationView) findViewById(R.id.main_activity_navigation_menu);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        toolbarHeading = (TextView) findViewById(R.id.main_activity_toolbar_text);
        toolbarHeading.setTypeface(AppUtils.getFancyTextTypeface(this));
        versionFrame = (FrameLayout) findViewById(R.id.activity_main_frame);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        onRecieveAppCheckedListeners = new ArrayList<>();
        initTabPager();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDrawerToggle();

        Intent intent=new Intent(this,AlarmBroadcastReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);


        //Variables Instantion


    }

    private void initDrawerToggle() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {


            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                //drawerOpened = false;
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
                //drawerOpened = true;
            }
        };

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }


    private void initTabPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), installedAppsList, allAppsList, this);
        viewPager.setAdapter(mainPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    public void registerRecieveAppCheckedListeners(OnRecieveAppCheckedListener onRecieveAppCheckedListener) {
        this.onRecieveAppCheckedListeners.add(onRecieveAppCheckedListener);
    }

    private void createSeparateLists(ArrayList<AppBean> appBeanList) {
        allAppsList = new ArrayList<>();
        installedAppsList = new ArrayList<>();
        for (int i = 0; i < appBeanList.size(); i++) {
            AppBean currentAppBean = appBeanList.get(i);

            if (!currentAppBean.isSystemApp())
                installedAppsList.add(currentAppBean);


            allAppsList.add(currentAppBean);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAppChecked(String packageName) {
        for (OnRecieveAppCheckedListener onRecieveAppCheckedListener : onRecieveAppCheckedListeners) {
            Log.d("Irshad", "Informing Fragments");
            onRecieveAppCheckedListener.onAppCheckedReceived(packageName);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent(this, DrawerActivity.class);

        switch (item.getItemId()) {
            case R.id.nav_change_theme:
                intent.setAction(DrawerActivity.THEME_CHANGE_ACTION);
                startActivity(intent);
                return false;
            case R.id.nav_change_password:
                intent.setAction(DrawerActivity.PASSWORD_CHANGE_ACTION);
                startActivity(intent);
                return false;
            case R.id.nav_mail_me:
                return false;
            case R.id.nav_settings:
                return false;

            default:
                return false;

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public static class AlarmBroadcastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {


        }
    }
}
