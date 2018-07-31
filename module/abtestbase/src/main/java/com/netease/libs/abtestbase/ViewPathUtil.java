package com.netease.libs.abtestbase;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class ViewPathUtil {

    public static String getViewPath(View v) {
        if (v == null || v.getContext() == null) {
            return null;
        }

        String result = (String) v.getTag(R.string.track_path_tag);
        if (!TextUtils.isEmpty(result)) {
            return result;
        }

        List<PathElement> paths = new ArrayList<>();

        View tmp = v;
        while (tmp != null) {
            int index = 0;
            int prefix;
            String viewClassName = tmp.getClass().getName();
            if (tmp.getId() == android.R.id.content) {
                index = 0;
                PathElement element = new PathElement();
                element.prefix = PathElement.STR_SHORTEST_PREFEX;
                element.view_class = viewClassName;
                element.index = index;
                element.viewId = android.R.id.content;
                element.contentDescription = null;
                element.tag = null;
                paths.add(0, element);
                break;
            }

            ViewParent parent = tmp.getParent();
            ViewGroup parentView = null;
            if (parent instanceof ViewGroup) {
                parentView = (ViewGroup) parent;
            }
            if (parentView == null) {
                break;
            }

            int count = parentView.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = parentView.getChildAt(i);
                if (child == tmp) {
                    index = i;
                    break;
                }
            }

            PathElement element = new PathElement();
            element.prefix = null;
            element.view_class = viewClassName;
            element.index = index;
            element.viewId = 0;
            element.contentDescription = null;
            element.tag = null;
            paths.add(0, element);

            tmp = parentView;
        }

        if (!paths.isEmpty()) {
            result = JsonUtil.toJSONString(paths);
            v.setTag(R.string.track_path_tag, result);
        }

        return result;
    }


    private static class PathElement {
        public static final String STR_SHORTEST_PREFEX = "shortest";

        public String prefix;
        public String view_class;
        public int index = -1;
        public int viewId = -1;
        public String contentDescription;
        public String tag;
    }
}
