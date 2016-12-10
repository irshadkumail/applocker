package com.androidclarified.applocker.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krazybee on 12/9/2016.
 */

public class JSONUtils {


    public static String getStringFromJSONObject(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key)) {
            return jsonObject.getString(key);
        }else
            return "";

    }


}
