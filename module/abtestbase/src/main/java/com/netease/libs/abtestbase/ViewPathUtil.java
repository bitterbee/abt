package com.netease.libs.abtestbase;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.netease.lib.abtest.util.CollectionUtil;
import com.netease.libs.abtestbase.layout.csslayout.StubCSSLayout;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        element.className = getClassName(v.getClass());
        element.environment = (String) v.getTag(R.string.abtest_tag_environment);
        element.idName = ABTestContext.getResEntryName(v.getId());
        if (v.getId() == android.R.id.content || v.getParent() == null) {
            element.index = 0;
            if (v.getContext() instanceof Activity) {
                element.environment = v.getContext().getClass().getName();
            } else if (v.getContext() instanceof ContextWrapper) {
                Context baseContext = ((ContextWrapper) v.getContext()).getBaseContext();
                if (baseContext instanceof Activity) {
                    element.environment = baseContext.getClass().getName();
                }
            }

            result.add(element);
            return result;
        }

        ViewParent parent = v.getParent();
        if (!(parent instanceof ViewGroup)) {
            element.environment = parent.getClass().getName();
            result.add(element);
            return result;
        }

        ViewGroup vg = (ViewGroup) v.getParent();

        boolean isRecyclerView = RefInvoker.isInstanceOf(vg, "android.support.v7.widget.RecyclerView");
        if (!isRecyclerView && v.getTag(R.string.abtest_tag_recycleview_holder_type) != null) {
            v.setTag(R.string.abtest_tag_recycleview_holder_type, null); // 父控件非 RecyclerView 就取消标记
            ABLog.e("clear abtest_tag_recycleview_holder_type");
        }

        boolean isListView = RefInvoker.isInstanceOf(vg, "android.widget.ListView");
        if (!isListView && v.getTag(R.string.abtest_tag_listview_type) != null) {
            v.setTag(R.string.abtest_tag_listview_type, null); // 父控件非 ListView 就取消标记
            ABLog.e("clear abtest_tag_listview_type");
        }

        boolean isViewPager = vg instanceof ViewPager;
        if (!isViewPager && v.getTag(R.string.abtest_tag_pager_position) != null) {
            v.setTag(R.string.abtest_tag_pager_position, null);
        }

        if (v.getTag(R.string.abtest_tag_recycleview_holder_type) != null) {
            element.type = (int) v.getTag(R.string.abtest_tag_recycleview_holder_type);
        } else if (v.getTag(R.string.abtest_tag_listview_type) != null) {
            element.type = (int) v.getTag(R.string.abtest_tag_listview_type);
        } else if (v.getTag(R.string.abtest_tag_pager_position) != null) {
            element.pageIndex = (int) v.getTag(R.string.abtest_tag_pager_position);
        } else {
            int count = vg.getChildCount();
            int index = 0;
            for (int i = 0; i < count; i++) {
                View child = vg.getChildAt(i);
                if (child == v) {
                    break;
                }
                if (child.getClass() == v.getClass() && child.getTag(R.string.abtest_tag_view_local_path) == null) {
                    index ++;
                }
            }
            element.index = index;
        }

        // guo/
        if (vg instanceof StubCSSLayout && vg.getParent() instanceof ViewGroup) {
            vg = (ViewGroup) vg.getParent();
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
        localItem.className = getClassName(v.getClass());
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

    private static final Map<Class, String> CLASSNAME_MAP = new HashMap<>();
    static {
        CLASSNAME_MAP.put(LinearLayout.class, "LinearLayout");
        CLASSNAME_MAP.put(FrameLayout.class, "FrameLayout");
        CLASSNAME_MAP.put(ViewGroup.class, "ViewGroup");
        CLASSNAME_MAP.put(ContentFrameLayout.class, "ContentFrameLayout");
        CLASSNAME_MAP.put(RelativeLayout.class, "RelativeLayout");
        CLASSNAME_MAP.put(GridLayout.class, "GridLayout");
        CLASSNAME_MAP.put(AbsoluteLayout.class, "AbsoluteLayout");
        CLASSNAME_MAP.put(TextView.class, "TextView");
        CLASSNAME_MAP.put(View.class, "View");
        CLASSNAME_MAP.put(ImageView.class, "ImageView");
        CLASSNAME_MAP.put(Button.class, "Button");
        CLASSNAME_MAP.put(ScrollView.class, "ScrollView");
        CLASSNAME_MAP.put(ListView.class, "ListView");
        CLASSNAME_MAP.put(ViewPager.class, "ViewPager");
        CLASSNAME_MAP.put(EditText.class, "EditText");
        CLASSNAME_MAP.put(Spinner.class, "Spinner");
        CLASSNAME_MAP.put(CheckBox.class, "CheckBox");
        CLASSNAME_MAP.put(DatePicker.class, "DatePicker");
        CLASSNAME_MAP.put(AppCompatTextView.class, "AppCompatTextView");
    }

    private static String getClassName(Class clazz) {
        String name = CLASSNAME_MAP.get(clazz);
        return name != null ? name : clazz.getName();
    }

    static void setXmlLayoutLocalPathTag(Context context, View view, int resource) {
        try {
            String resourceName = ABTestContext.getResEntryName(resource);
            markLocalViewPaths(view, 0, resourceName, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PathElement {
        public String resName;
        public String className;
        public String idName;
        public Integer index; // 普通view 在 parent的相同类型view的index
        public Integer type; // RecycleView, ListView. ViewHolder Type
        public Integer pageIndex; // view 在 viewpager 的复用位置
        public String environment; // Fragment 等
    }
}