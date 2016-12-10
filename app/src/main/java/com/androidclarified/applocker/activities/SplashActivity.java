package com.androidclarified.applocker.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidclarified.applocker.R;
import com.androidclarified.applocker.fragments.ChangePasswordFragment;
import com.androidclarified.applocker.fragments.GoogleLoginFrag;
import com.androidclarified.applocker.listeners.OverlayScreenListener;
import com.androidclarified.applocker.model.AppBean;
import com.androidclarified.applocker.utils.AppSharedPreferences;
import com.androidclarified.applocker.utils.AppUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements OverlayScreenListener {

    private MainActivityStarter mainActivityStarter;
    private Handler handler;
    private TextView appName;
    private FrameLayout fragmentFrame;
    private ArrayList<AppBean> appBeanList = new ArrayList<>();
    private ChangePasswordFragment changePasswordFragment;


    public final static String APP_BEAN_LIST = "app_bean_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();


    }

    public void init() {
        mainActivityStarter = new MainActivityStarter();
        handler = new Handler();
        appName = (TextView) findViewById(R.id.activity_splash_text);
        fragmentFrame = (FrameLayout) findViewById(R.id.splash_activity_fragment_frame);
        appName.setTypeface(AppUtils.getFancyTextTypeface(this));
        handler.postDelayed(mainActivityStarter, 2000);
    }

    private void getInstalledApps() {
        PackageManager packageManager = getPackageManager();
        Intent myintent=new Intent(Intent.ACTION_MAIN,null);
        myintent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> applicationInfoList = packageManager.queryIntentActivities(myintent,PackageManager.GET_META_DATA);

        for (int i = 0; i < applicationInfoList.size(); i++) {
            ActivityInfo currentApplicationInfo = applicationInfoList.get(i).activityInfo;

            String appLabel = (String) currentApplicationInfo.loadLabel(packageManager);
            String packageName = currentApplicationInfo.packageName;
            boolean isChecked = false;
            boolean isSystemApp = false;
            if (AppSharedPreferences.getAppSharedPreference(this, packageName))
                isChecked = true;
            if (isSystemApp(applicationInfoList.get(i)))
                isSystemApp = true;


            AppBean appBean = new AppBean(packageName, appLabel, isChecked, isSystemApp);



                appBeanList.add(appBean);
        }
        if (!AppSharedPreferences.getFirstTimePreference(this)) {
            addGoogleLoginFragment();
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(APP_BEAN_LIST, appBeanList);
            startActivity(intent);
        }
    }

    private void addGoogleLoginFragment()
    {
        GoogleLoginFrag googleLoginFrag=new GoogleLoginFrag();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.splash_activity_fragment_frame,googleLoginFrag);
        fragmentTransaction.commit();


    }
    public void addPasswordFragment() {
        changePasswordFragment = new ChangePasswordFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.splash_activity_fragment_frame, changePasswordFragment);
        fragmentTransaction.commit();
    }

    private boolean isSystemApp(ResolveInfo myapplicationInfo) {
        return ((myapplicationInfo.activityInfo.applicationInfo.flags & (ApplicationInfo.FLAG_SYSTEM)) != 0);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private static Bitmap processDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void showOverlayScreen() {

    }

    @Override
    public void hideOverlayScreen() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra(APP_BEAN_LIST, appBeanList);
        startActivity(intent);
        AppSharedPreferences.putFirstTimePreference(this, true);
        changePasswordFragment.hideOverlay();
    }


    private class MainActivityStarter implements Runnable {
        @Override
        public void run() {
            getInstalledApps();


        }
    }

}
