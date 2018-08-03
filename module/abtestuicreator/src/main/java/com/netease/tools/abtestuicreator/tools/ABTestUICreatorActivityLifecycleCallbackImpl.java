package com.netease.tools.abtestuicreator.tools;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.util.LongClickReplaceUtil;
import com.netease.tools.abtestuicreator.view.MovableLayout;
import com.netease.tools.abtestuicreator.view.SwitchManager;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ABTestUICreatorActivityLifecycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    private int mSwitchBtnId = View.NO_ID;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);

        replaceTraversal(content);
        content.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                replaceTraversal(child);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });

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

    private void replaceTraversal(View v) {
        if (SwitchManager.getInstance().isOpen()) {
            LongClickReplaceUtil.performTraversal(v, true);
        }
        SwitchManager.getInstance().register(v);
    }
}
