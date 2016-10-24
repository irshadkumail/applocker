package com.androidclarified.applocker.model;

/**
 * Created by My Pc on 10/19/2016.
 */

public class ActivityBean {

    private String packName;
    private String activityLabel;


    public ActivityBean(String packName,String activityLabel) {

        this.activityLabel=activityLabel;
        this.packName=packName;

    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }
}
