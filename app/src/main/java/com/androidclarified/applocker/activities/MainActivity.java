package com.androidclarified.applocker.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.adapters.MainPagerAdapter;
import com.androidclarified.applocker.fragments.PermissionFragment;
import com.androidclarified.applocker.listeners.OnAppCheckedListener;
import com.androidclarified.applocker.listeners.OnRecieveAppCheckedListener;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.services.AppCheckerService;
import com.androidclarified.applocker.utils.AppConstants;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.utils.AppUtils;
import com.androidclarified.applocker.utils.JSONUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnAppCheckedListener,
        NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN_REQUEST_CODE = 101;


    private SwitchCompat startSwitch;
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
    private FrameLayout navigationHeader;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private Intent serviceIntent;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    private PermissionFragment permissionFragment;
    private ArrayList<OnRecieveAppCheckedListener> onRecieveAppCheckedListeners;
    public static PendingIntent pendingIntent;
    public AlarmManager alarmManager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appBeanList = getIntent().getExtras().getParcelableArrayList(SplashActivity.APP_BEAN_LIST);
        createSeparateLists(appBeanList);
        initViews();


    }

    public void onResumeFragments() {
        super.onResumeFragments();
        initPermission();

    }

    private void initPermission() {

        if (!AppUtils.isUsagePermissionGranted(this) || !AppUtils.canDrawOverlay(this)) {
            addFragment(permissionFragment);
        } else {
            removeFragment(permissionFragment);
        }

    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_frame, fragment);
        fragmentTransaction.addToBackStack(PermissionFragment.PERMISSION_FRAG_TAG);
        fragmentTransaction.commit();

    }

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean checkForPermission() {
        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
        int mode = appOpsManager.checkOp(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        return (mode == AppOpsManager.MODE_ALLOWED);
    }

    private void startCheckingforApps() {
        startService(serviceIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000) * (60) * (10), pendingIntent);
    }

    private void stopAppCheck() {
        stopService(serviceIntent);
        AppSharedPreferences.putServiceRunningPreference(this,false);
        alarmManager.cancel(pendingIntent);

    }

    private void initViews() {

        //Views Instantiation


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        permissionFragment = new PermissionFragment();
        navigationView = (NavigationView) findViewById(R.id.main_activity_navigation_menu);
        startSwitch = (SwitchCompat) navigationView.getMenu().findItem(R.id.nav_service_switch).getActionView();
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        toolbarHeading = (TextView) findViewById(R.id.main_activity_toolbar_text);
        toolbarHeading.setTypeface(AppUtils.getFancyTextTypeface(this));
        versionFrame = (FrameLayout) findViewById(R.id.activity_main_frame);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        navigationHeader = (FrameLayout) navigationView.getHeaderView(0);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        startSwitch.setOnCheckedChangeListener(this);
        onRecieveAppCheckedListeners = new ArrayList<>();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .enableAutoManage(this, this)
                .build();


        initTabPager();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDrawerToggle();
        setNavigationHeader();
        serviceIntent = new Intent(MainActivity.this, AppCheckerService.class);


        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (AppSharedPreferences.getServiceRunningPreferencec(this))
            startSwitch.setChecked(true);


        //Variables Instantion
    }

    private void setNavigationHeader() {
        try {
            CircleImageView circleImageView = (CircleImageView) navigationHeader.findViewById(R.id.iv_user_profile_pic);
            TextView displayName = (TextView) navigationHeader.findViewById(R.id.tv_user_display_name);
            TextView emailText = (TextView) navigationHeader.findViewById(R.id.tv_user_email);

            if (!AppSharedPreferences.getUserInfoPreferences(this).equalsIgnoreCase(AppConstants.USER_INFO_EMPTY)) {
                JSONObject jsonObject = new JSONObject(AppSharedPreferences.getUserInfoPreferences(this));
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                displayName.setText(JSONUtils.getStringFromJSONObject(jsonObject, "display_name"));
                emailText.setText(JSONUtils.getStringFromJSONObject(jsonObject, "email"));
                String imagePath = JSONUtils.getStringFromJSONObject(jsonObject, "user_pic");
                if (!imagePath.isEmpty())
                    Picasso.with(this).load(imagePath).error(R.drawable.default_pic).fit().centerCrop().into(circleImageView);
                else
                    Picasso.with(this).load(R.drawable.default_pic).fit().centerCrop().into(circleImageView);
            } else {
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                displayName.setText("Login ");
                emailText.setText("");
                Picasso.with(this).load(R.drawable.default_pic).fit().centerCrop().into(circleImageView);
                navigationHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        startActivityForResult(intent, SIGN_IN_REQUEST_CODE);

                    }
                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SIGN_IN_REQUEST_CODE:
                    GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    handleIntentResult(googleSignInResult);
                    break;
            }
        }
    }

    private void handleIntentResult(GoogleSignInResult googleSignInResult) {
        try {
            if (googleSignInResult.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                JSONObject userInfoJson = new JSONObject();
                userInfoJson.put("display_name", googleSignInAccount.getDisplayName());
                userInfoJson.put("email", googleSignInAccount.getEmail());
                userInfoJson.put("user_pic", googleSignInAccount.getPhotoUrl());
                AppSharedPreferences.putUserInfoPreference(this, userInfoJson.toString());
                setNavigationHeader();
            } else {
                Toast.makeText(this, "Sign In Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setMessage("Do you want to quit AppLocker?")
                .setTitle("Quit AppLocker")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());


                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();

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
            Intent intent = new Intent(this, DrawerActivity.class);
            intent.setAction(DrawerActivity.SETTING_ACTION);
            startActivity(intent);
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
                intent.setAction(DrawerActivity.SETTING_ACTION);
                startActivity(intent);
                return false;
            case R.id.nav_logout:
                Auth.GoogleSignInApi.signOut(googleApiClient);
                AppSharedPreferences.putUserInfoPreference(this, AppConstants.USER_INFO_EMPTY);
                setNavigationHeader();

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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if (isChecked) {
            startCheckingforApps();
        } else {
            stopAppCheck();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static class AlarmBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Irshad","AlarmBroadcastReceiver Started "+AppSharedPreferences.getServiceRunningPreferencec(context)+", "+AppCheckerService.isServiceRunning);

            Toast.makeText(context,"AlarmBroadcastReceiver",Toast.LENGTH_SHORT).show();

            if (AppSharedPreferences.getServiceRunningPreferencec(context) && !AppCheckerService.isServiceRunning) {

                Intent serviceIntent=new Intent(context,AppCheckerService.class);
                context.startService(serviceIntent);

                if (intent.getAction()!=null && intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")){
                    AlarmManager alarmManager= (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),(60)*(1000)*(10),pendingIntent);

                }



            }

        }
    }
}
