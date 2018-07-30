package com.netease.lib.abtest.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.netease.lib.abtest.R;
import com.netease.lib.abtest.model.ABTestUICase;
import com.netease.lib.abtest.model.UIProp;
import com.netease.lib.abtest.ui.attr.AttrSetter;
import com.netease.lib.abtest.ui.attr.SkinAttrFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/29.
 */
public class ABTestActivityLiftcycleCallbackImpl implements Application.ActivityLifecycleCallbacks {

    private Map<String, ABTestUICase> mUICases = new HashMap<>();
    private SkinAttrFactory mSkinAttrFactory = new SkinAttrFactory();

    public ABTestActivityLiftcycleCallbackImpl(List<ABTestUICase> uiCases) {
        if (uiCases != null) {
            for (ABTestUICase uiCase : uiCases) {
                mUICases.put(uiCase.getViewPath(), uiCase);
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ViewGroup content = activity.findViewById(android.R.id.content);
        applyView(content);
        content.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                applyView(child);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
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

    private void applyView(View v) {
        if (v == null || v.getContext() == null) {
            return;
        }

        Object tag = v.getTag(R.string.abtest_ui_apply);
        if (tag != null) {
            return;
        }

        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                int count = vg.getChildCount();
                for (int i=0; i<count; i++) {
                    View child = vg.getChildAt(i);
                    applyView(child);
                }

                return;
            }

            String path = ViewPathUtil.getViewPath(v);
            ABTestUICase uiCase = mUICases.get(path);
            if (uiCase == null) {
                return;
            }

            for (UIProp prop : uiCase.getUiProps()) {
                AttrSetter attr = mSkinAttrFactory.getAttr(prop.attrName);
                if (attr != null) {
                    attr.apply(v, prop);
                }
            }
        } finally {
            v.setTag(R.string.abtest_ui_apply, true);
        }

    }
}
