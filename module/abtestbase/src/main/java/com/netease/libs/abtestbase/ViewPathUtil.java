package com.netease.libs.abtestbase;

import android.content.Context;
import android.support.v7.widget.ContentFrameLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.netease.lib.abtest.util.CollectionUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class ViewPathUtil {

    private static boolean hasLocalPath(View v) {
        return v.getTag(R.string.abtest_tag_view_root_xmllayout) == null &&
                v.getTag(R.string.abtest_tag_view_local_path) != null;
    }

    public static String getViewPath(View v) {
        List<PathElement> elements = getViewPathList(v);
        return CryptoUtil.getSHA256(JsonUtil.toJSONString(elements));
    }

    private static List<PathElement> getViewPathList(View v) {

        List<PathElement> result = new LinkedList<>();
        if (v == null || v.getContext() == null) {
            return result;
        }
        if (hasLocalPath(v)) {
            List<PathElement> localPath = new LinkedList<>();
            localPath.addAll((List<PathElement>) v.getTag(R.string.abtest_tag_view_local_path));
            View localRoot = getLocalRoot(v);
            if (localRoot == null) {
                v.setTag(R.string.abtest_tag_view_local_path, null);
            }
            if (localRoot != null) {
                List<PathElement> rootPath = getViewPathList(localRoot);
                if (!localPath.isEmpty() && !rootPath.isEmpty()) {
                    PathElement rootElement = localPath.remove(0);
                    CollectionUtil.lastItem(rootPath).resName = rootElement.resName;
                }

                result.addAll(rootPath);
                result.addAll(localPath);
                return result;
            }
        }

        PathElement element = new PathElement();
        element.className = v.getClass().getName();
        if (v.getId() == android.R.id.content || v.getParent() == null) {
            element.index = 0;

            result.add(element);
            return result;
        }

        ViewGroup vg = (ViewGroup) v.getParent();
        if (TextUtils.equals(vg.getClass().getName(), "android.support.v7.widget.RecyclerView") &&
                v.getTag(R.string.abtest_tag_recycleview_holder_type) != null) {
            element.type = (int) v.getTag(R.string.abtest_tag_recycleview_holder_type);
        } else {
            int count = vg.getChildCount();
            int index = 0;
            for (int i = 0; i < count; i++) {
                View child = vg.getChildAt(i);
                if (child == v) {
                    break;
                }
                if (child.getClass() == v.getClass()) {
                    index ++;
                }
            }
            element.index = index;
        }

        result = getViewPathList(vg);
        result.add(element);
        return result;
    }

    private static View getLocalRoot(View v) {
        if (v == null) {
            return null;
        }
        List<PathElement> localPath = (List<PathElement>) v.getTag(R.string.abtest_tag_view_local_path);
        if (localPath == null) {
            return null;
        }

        boolean isFound = false;
        ViewGroup vg = (ViewGroup) v.getParent();
        while (vg != null && vg.getId() != android.R.id.content) {
            if (vg.getTag(R.string.abtest_tag_view_local_path) != null &&
                    vg.getTag(R.string.abtest_tag_view_root_xmllayout) != null) {
                isFound = true;
                break;
            }
            vg = (ViewGroup) vg.getParent();
        }
        return isFound ? vg : null;
    }

    public static void markLocalViewPaths(View v, int index, String resourceName, List<PathElement> parentLocalPath) {
        if (v == null) {
            return;
        }

        PathElement localItem = new PathElement();
        localItem.resName = resourceName;
        localItem.className = v.getClass().getName();
        localItem.index = index;

        List<PathElement> localPath = new LinkedList<>();
        if (parentLocalPath != null) {
            localPath.addAll(parentLocalPath);
        }
        localPath.add(localItem);

        v.setTag(R.string.abtest_tag_view_local_path, localPath);

        if (parentLocalPath == null) {
            v.setTag(R.string.abtest_tag_view_root_xmllayout, true);
        }

        ViewGroup vg = v instanceof ViewGroup ? (ViewGroup) v: null;
        if (vg == null) {
            return;
        }

        int count = vg.getChildCount();
        for (int i=0; i<count; i++) {
            View child = vg.getChildAt(i);
            if (!(child instanceof ContentFrameLayout)) {
                markLocalViewPaths(child, i, resourceName, localPath);
            }
        }
    }

    static void setXmlLayoutLocalPathTag(Context context, View view, int resource) {
        try {
            String resourceName = context.getResources().getResourceName(resource);
            markLocalViewPaths(view, 0, resourceName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //    public static String getViewPath(View v) {
//        if (v == null || v.getContext() == null) {
//            return null;
//        }
//
//        String result = (String) v.getTag(R.string.view_path_tag);
//        if (!TextUtils.isEmpty(result)) {
//            return result;
//        }
//
//        List<PathElement> paths = new ArrayList<>();
//
//        View tmp = v;
//        while (tmp != null) {
//            int index = 0;
//            String viewClassName = tmp.getClass().getName();
//            if (tmp.getId() == android.R.id.content) {
//                index = 0;
//                PathElement element = new PathElement();
//                element.view_class = viewClassName;
//                element.index = index;
//                paths.add(0, element);
//                break;
//            }
//
//            ViewParent parent = tmp.getParent();
//            ViewGroup parentView = null;
//            if (parent instanceof ViewGroup) {
//                parentView = (ViewGroup) parent;
//            }
//            if (parentView == null) {
//                break;
//            }
//
//            int count = parentView.getChildCount();
//            int sameViewCount = 0;
//            for (int i = 0; i < count; i++) {
//                View child = parentView.getChildAt(i);
//                if (child == tmp) {
//                    index = sameViewCount;
//                    break;
//                }
//                if (child.getClass() == tmp.getClass()) {
//                    sameViewCount ++;
//                }
//            }
//
//            PathElement element = new PathElement();
//            element.view_class = viewClassName;
//            element.index = index;
//            paths.add(0, element);
//
//            tmp = parentView;
//        }
//
//        if (!paths.isEmpty()) {
//            result = CryptoUtil.getSHA256(JsonUtil.toJSONString(paths));
//            v.setTag(R.string.view_path_tag, result);
//        }
//
//        return result;
//    }

    private static class PathElement {
        public String resName;
        public String className;
        public Integer index;
        public Integer type; // RecycleView, ListView. View Type
    }
}