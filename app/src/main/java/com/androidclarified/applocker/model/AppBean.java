package com.androidclarified.applocker.model;

import android.graphics.drawable.Drawable;

/**
 * Created by My Pc on 10/19/2016.
 */

public class AppBean {

    private String packName;
    private Drawable appIcon;
    private String appLabel;
    private boolean isChecked;


    public AppBean(String packName, Drawable appIcon, String appLabel, boolean isChecked) {
        this.appIcon=appIcon;
        this.packName=packName;
        this.appLabel=appLabel;
        this.isChecked = isChecked;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }



    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
