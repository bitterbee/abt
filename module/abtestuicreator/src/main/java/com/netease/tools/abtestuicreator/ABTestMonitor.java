package com.netease.tools.abtestuicreator;

import android.app.Application;

import com.netease.tools.abtestuicreator.tools.ABTestUICreatorActivityLifecycleCallbackImpl;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class ABTestMonitor {

    private ABTestUICreatorActivityLifecycleCallbackImpl mActivityLifecycleCallback;

    private static ABTestMonitor sInstance = null;

    public static ABTestMonitor getInstance() {
        if (sInstance == null) {
            synchronized (ABTestMonitor.class) {
                if (sInstance == null) {
                    sInstance = new ABTestMonitor();
                }
            }
        }
        return sInstance;
    }

    private ABTestMonitor() {
    }

    public void init(Application app) {
        mActivityLifecycleCallback = new ABTestUICreatorActivityLifecycleCallbackImpl();
        app.registerActivityLifecycleCallbacks(mActivityLifecycleCallback);
    }
}