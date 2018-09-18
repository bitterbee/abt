package com.netease.lib.abtest.ui;

import android.view.View;
import android.view.ViewGroup;

import com.netease.lib.abtest.R;
import com.netease.lib.abtest.ui.prop.IPropSetter;
import com.netease.lib.abtest.ui.prop.UIPropFactory;
import com.netease.libs.abtestbase.ViewPathUtil;
import com.netease.libs.abtestbase.anno.ABTestIgnore;
import com.netease.libs.abtestbase.model.ABTestUICase;
import com.netease.libs.abtestbase.model.UIProp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl06 on 2018/8/4.
 */

public class UIPropSetterMgr implements View.OnAttachStateChangeListener {

    private static Map<String, ABTestUICase> sUICases = new HashMap<>();
    private static UIPropFactory sUIPropFactory = new UIPropFactory();

    public static void init(List<ABTestUICase> uiCases) {
        if (uiCases != null) {
            for (ABTestUICase uiCase : uiCases) {
                sUICases.put(uiCase.getViewPath(), uiCase);
            }
        }
    }

    private static UIPropSetterMgr sInstance = null;

    public static UIPropSetterMgr getInstance() {
        if (sInstance == null) {
            synchronized (UIPropSetterMgr.class) {
                if (sInstance == null) {
                    sInstance = new UIPropSetterMgr();
                }
            }
        }
        return sInstance;
    }

    private UIPropSetterMgr() {
    }

    public void applyView(View v) {
        if (v == null || v.getContext() == null) {
            return;
        }

        ABTestIgnore ignoreAnno = v.getClass().getAnnotation(ABTestIgnore.class);
        if (ignoreAnno != null) {
            return;
        }

        Object tag = v.getTag(R.string.abtest_tag_ui_apply);
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

                if (v.getTag(R.string.abtest_tag_ui_layout_listener) == null) {
                    vg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            clearApplyTag(v);
                            applyView(v);
                        }
                    });
                    v.setTag(R.string.abtest_tag_ui_layout_listener, true);
                }

                return;
            }
            if (v.getWindowToken() == null && v.getTag(R.string.abtest_tag_ui_attach_window_listener) == null) {
                v.setTag(R.string.abtest_tag_ui_attach_window_listener, true);
                v.addOnAttachStateChangeListener(this);
            }

            String path = ViewPathUtil.getViewPath(v);
            ABTestUICase uiCase = sUICases.get(path);
            if (uiCase == null) {
                return;
            }

            for (UIProp prop : uiCase.getUiProps()) {
                IPropSetter setter = sUIPropFactory.getPropSetter(prop.name);
                if (setter != null) {
                    setter.apply(v, prop);
                }
            }
        } finally {
            markApplyTag(v);
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        clearApplyTag(v);
        applyView(v);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        clearApplyTag(v);
        v.setTag(R.string.abtest_tag_ui_attach_window_listener, null);
        v.removeOnAttachStateChangeListener(this);
    }

    private void clearApplyTag(View v) {
        v.setTag(R.string.abtest_tag_ui_apply, null);
    }

    private void markApplyTag(View v) {
        v.setTag(R.string.abtest_tag_ui_apply, true);
    }
}
