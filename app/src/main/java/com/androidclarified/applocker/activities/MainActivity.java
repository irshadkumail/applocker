package com.androidclarified.applocker.activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.AppListAdapter;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.utils.AppSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView appList;
    private AppListAdapter appListAdapter;
    private List<AppBean> appBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getInstalledApps();



    }

    public void initViews() {

        //Views Instantiation
        fab = (FloatingActionButton) findViewById(R.id.fab);
        appList = (RecyclerView) findViewById(R.id.activity_main_recycler);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Variables Instantion
        appBeanList=new ArrayList<AppBean>();



        setSupportActionBar(toolbar);
        appList.setLayoutManager(new LinearLayoutManager(this));


    }

    public void getInstalledApps() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i = 0; i < applicationInfoList.size(); i++) {

            Drawable appIcon=applicationInfoList.get(i).loadIcon(packageManager);
            String appLabel = (String) applicationInfoList.get(i).loadLabel(packageManager);
            String packageName = applicationInfoList.get(i).packageName;
            boolean isChecked=false;
            if(AppSharedPreferences.getAppSharedPreference(this,packageName))
                isChecked=true;

            AppBean appBean=new AppBean(packageName,appIcon,appLabel, isChecked);
            appBeanList.add(appBean);


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
}
