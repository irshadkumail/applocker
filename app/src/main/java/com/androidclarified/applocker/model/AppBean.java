package com.androidclarified.applocker.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by My Pc on 10/19/2016.
 */

public class AppBean implements Parcelable {

    private String packName;
    private String appLabel;
    private boolean isChecked;
    private boolean isSystemApp;


    public AppBean(Parcel parcel) {
        packName = parcel.readString();
        appLabel = parcel.readString();
        isChecked = convertIntToBoolean(parcel.readInt());
        isSystemApp = convertIntToBoolean(parcel.readInt());
    }

    public AppBean(String packName,String appLabel, boolean isChecked, boolean isSystemApp) {

        this.packName = packName;
        this.appLabel = appLabel;
        this.isChecked = isChecked;
        this.isSystemApp = isSystemApp;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(packName);
        dest.writeString(appLabel);
        dest.writeInt(convertBooleanToInt(isChecked));
        dest.writeInt(convertBooleanToInt(isSystemApp));

    }

    public static Parcelable.Creator CREATOR = new Creator() {
        @Override
        public AppBean createFromParcel(Parcel source) {
            return new AppBean(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };


    private boolean convertIntToBoolean(int i) {
        if (i == 1)
            return true;
        else
            return false;

    }

    private int convertBooleanToInt(boolean b) {
        if (b)
            return 1;
        else
            return 0;

    }

}
