package com.netease.tools.abtestuicreator.util;

import android.util.Log;

import com.netease.tools.abtestuicreator.BuildConfig;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class LogUtil {
    private static final String TAG = "ABTestUICreator";

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }
}
