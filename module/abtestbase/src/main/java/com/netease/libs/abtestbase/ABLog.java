package com.netease.libs.abtestbase;

import android.util.Log;

/**
 * Created by zyl06 on 2018/7/26.
 */

public class ABLog {

    private static final String TAG = "ABTest";

    public static void e(Throwable e) {
        e(e.toString());
    }

    public static void e(String info) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, info);
        }
    }

    public static void i(String info) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, info);
        }
    }
}
