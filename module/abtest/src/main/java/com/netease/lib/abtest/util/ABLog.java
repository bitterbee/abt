package com.netease.lib.abtest.util;

import android.util.Log;
import com.netease.lib.abtest.BuildConfig;

/**
 * Created by zyl06 on 2018/7/26.
 */

public class ABLog {

    private static final String TAG = "ABTest";

    public static void e(Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.toString());
        }
    }

    public static void i(String info) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, info);
        }
    }
}
