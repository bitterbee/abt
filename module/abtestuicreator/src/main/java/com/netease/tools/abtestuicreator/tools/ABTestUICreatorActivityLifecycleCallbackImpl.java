package com.netease.tools.abtestuicreator.tools;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.netease.libs.abtestbase.RefInvoker;
import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.view.MovableLayout;
import com.netease.tools.abtestuicreator.view.SwitchManager;

import java.sql.Ref;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ABTestUICreatorActivityLifecycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    private int mSwitchBtnId = View.NO_ID;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        replaceActivityLayoutInflater(activity);
        mSwitchBtnId = R.id.id_switch_btn;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        MovableLayout switchButton = SwitchManager.getInstance().newSwitchButton(
                decorView, activity);
        switchButton.setId(mSwitchBtnId);

        decorView.addView(switchButton);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        View switchBtn = activity.findViewById(mSwitchBtnId);
        if (switchBtn != null) {
            decorView.removeView(switchBtn);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private void replaceActivityLayoutInflater(Activity activity) {
        LayoutInflater inflater0 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (!(inflater0 instanceof ToolLayoutInflater)) {
            LayoutInflater proxyInflater = new ToolLayoutInflater(inflater0);
            RefInvoker.setFieldObject(activity, ContextThemeWrapper.class, "mInflater", proxyInflater);
        }

        Window window = activity.getWindow();
        LayoutInflater inflater1 = activity.getWindow().getLayoutInflater();
        if (!(inflater1 instanceof ToolLayoutInflater)) {
            LayoutInflater proxyInflater = new ToolLayoutInflater(inflater1);

            if (RefInvoker.isInstanceOf(window, "com.android.internal.policy.PhoneWindow")) {
                RefInvoker.setFieldObject(window, "com.android.internal.policy.PhoneWindow", "mLayoutInflater", proxyInflater);
            } else if (RefInvoker.isInstanceOf(window, "com.android.internal.policy.impl.PhoneWindow")) {
                RefInvoker.setFieldObject(window, "com.android.internal.policy.impl.PhoneWindow", "mLayoutInflater", proxyInflater);
            }
        }
    }
}
