package com.androidclarified.applocker.activities;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.MainPagerAdapter;
import com.androidclarified.applocker.listeners.OnAppCheckedListener;
import com.androidclarified.applocker.listeners.OnRecieveAppCheckedListener;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.services.AppCheckerService;
import com.androidclarified.applocker.utils.AppSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnAppCheckedListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainPagerAdapter mainPagerAdapter;
    private ArrayList<AppBean> allAppsList;
    private ArrayList<AppBean> installedAppsList;
    private ArrayList<AppBean> appBeanList;
    private ArrayList<OnRecieveAppCheckedListener> onRecieveAppCheckedListeners;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBeanList = getIntent().getExtras().getParcelableArrayList(SplashActivity.APP_BEAN_LIST);
        createSeparateLists(appBeanList);
        initViews();


        startService(new Intent(MainActivity.this, AppCheckerService.class));

    }

    public void initViews() {

        //Views Instantiation

        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        setSupportActionBar(toolbar);
        initTabPager();
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //Variables Instantion


    }


    private void initTabPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), installedAppsList, allAppsList);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAppChecked(String packageName) {
        for (OnRecieveAppCheckedListener onRecieveAppCheckedListener : onRecieveAppCheckedListeners) {
            onRecieveAppCheckedListener.onAppCheckedReceived(packageName);
        }

    }
}
