package com.netease.lib.abtest.ui;

import android.view.View;
import android.view.ViewGroup;

import com.netease.lib.abtest.R;
import com.netease.lib.abtest.ui.prop.PropSetter;
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

public class UIPropSetter {

    private static Map<String, ABTestUICase> sUICases = new HashMap<>();
    private static UIPropFactory sUIPropFactory = new UIPropFactory();

    public static void init(List<ABTestUICase> uiCases) {
        if (uiCases != null) {
            for (ABTestUICase uiCase : uiCases) {
                sUICases.put(uiCase.getViewPath(), uiCase);
            }
        }
    }

    public static void applyView(View v) {
        if (v == null || v.getContext() == null) {
            return;
        }

        ABTestIgnore ignoreAnno = v.getClass().getAnnotation(ABTestIgnore.class);
        if (ignoreAnno != null) {
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

                if (v.getTag(R.string.abtest_ui_layout_listener) == null) {
                    vg.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            clearApplyTag(v);
                            applyView(v);
                        }
                    });
                    v.setTag(R.string.abtest_ui_layout_listener, true);
                }

                return;
            }

            String path = ViewPathUtil.getViewPath(v);
            ABTestUICase uiCase = sUICases.get(path);
            if (uiCase == null) {
                return;
            }

            for (UIProp prop : uiCase.getUiProps()) {
                PropSetter setter = sUIPropFactory.getPropSetter(prop.name);
                if (setter != null) {
                    setter.apply(v, prop);
                }
            }
        } finally {
            markApplyTag(v);
        }
    }

    private static void clearApplyTag(View v) {
        v.setTag(R.string.abtest_ui_apply, null);
    }

    private static void markApplyTag(View v) {
        v.setTag(R.string.abtest_ui_apply, true);
    }
}
