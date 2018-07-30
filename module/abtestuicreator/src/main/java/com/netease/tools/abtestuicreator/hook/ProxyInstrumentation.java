package com.netease.tools.abtestuicreator.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ViewGroup;

import com.netease.tools.abtestuicreator.view.MovableLayout;
import com.netease.tools.abtestuicreator.view.SwitchManager;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ProxyInstrumentation extends Instrumentation {

    private MovableLayout mSwitchButton;

    public void callActivityOnCreate(Activity activity, Bundle icicle,
                                     PersistableBundle persistentState) {
        super.callActivityOnCreate(activity, icicle, persistentState);
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        super.callActivityOnCreate(activity, icicle);
    }

    @Override
    public void callActivityOnResume(Activity activity) {
        super.callActivityOnResume(activity);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        mSwitchButton = SwitchManager.getInstance().newSwitchButton(
                decorView, activity);
        decorView.addView(mSwitchButton);
    }

    @Override
    public void callActivityOnPause(Activity activity) {
        super.callActivityOnPause(activity);
        if (mSwitchButton == null) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.removeView(mSwitchButton);
    }
}