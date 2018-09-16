package com.netease.demo.abtest;

import android.app.Application;

import com.netease.lib.abtest.ABTestConfig;
import com.netease.libs.abtestbase.ABTestFileUtil;
import com.netease.tools.abtestuicreator.ABTestMonitor;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ABTestMonitor.getInstance().init(this);
    }
}
