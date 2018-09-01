package com.netease.libs.abtestbase;

import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/9/1.
 */

public class ViewUtil {

    public static int removeFromParent(View view) {
        int result = -1;
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent == null) {
            return result;
        }

        int childCount = parent.getChildCount();
        for (int i=0; i<childCount; i++) {
            if (parent.getChildAt(i) == view) {
                result = i;
            }
        }

        parent.removeView(view);
        return result;
    }

    public static List<View> getChildren(ViewGroup vg) {
        List<View> children = new LinkedList<>();
        int count = vg.getChildCount();
        for (int i=0; i<count; i++) {
            View child = vg.getChildAt(i);
            children.add(child);
        }

        return children;
    }
}
