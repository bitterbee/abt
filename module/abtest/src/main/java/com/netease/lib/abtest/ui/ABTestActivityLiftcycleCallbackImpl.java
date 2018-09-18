package com.netease.lib.abtest.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.netease.libs.abtestbase.RefInvoker;
import com.netease.libs.abtestbase.anno.ABTestIgnore;

/**
 * Created by zyl06 on 2018/7/29.
 */
public class ABTestActivityLiftcycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    public ABTestActivityLiftcycleCallbackImpl() {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ABTestIgnore ignoreAnno = activity.getClass().getAnnotation(ABTestIgnore.class);
        if (ignoreAnno != null) {
            return;
        }

//        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
//        UIPropSetterMgr.applyView(content);
//        content.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
//            @Override
//            public void onChildViewAdded(View parent, View child) {
//                UIPropSetterMgr.applyView(child);
//            }
//
//            @Override
//            public void onChildViewRemoved(View parent, View child) {
//
//            }
//        });

        View content = activity.findViewById(android.R.id.content);
        UIPropSetterMgr.getInstance().applyView(content);
        replaceActivityLayoutInflater(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

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
        if (!(inflater0 instanceof ABTestProxyLayoutInflater)) {
            LayoutInflater proxyInflater = new ABTestProxyLayoutInflater(inflater0);
            RefInvoker.setFieldObject(activity, ContextThemeWrapper.class, "mInflater", proxyInflater);
        }

        Window window = activity.getWindow();
        LayoutInflater inflater1 = activity.getWindow().getLayoutInflater();
        if (!(inflater1 instanceof ABTestProxyLayoutInflater)) {
            LayoutInflater proxyInflater = new ABTestProxyLayoutInflater(inflater1);
            RefInvoker.setFieldObject(window, "com.android.internal.policy.PhoneWindow", "mLayoutInflater", proxyInflater);
        }
    }
}
