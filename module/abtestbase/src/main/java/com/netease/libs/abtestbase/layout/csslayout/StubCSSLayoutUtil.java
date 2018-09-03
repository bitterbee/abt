package com.netease.libs.abtestbase.layout.csslayout;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.csslayout.CSSNode;
import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.JsonUtil;
import com.netease.libs.abtestbase.ViewUtil;
import com.netease.libs.abtestbase.model.CSSNodeModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/8/31.
 */

public class StubCSSLayoutUtil {

    public static boolean applyCssLayout(ViewGroup vg, String json) {
        CSSNodeModel model = JsonUtil.parse(json, CSSNodeModel.class);
        if (model == null) {
            ABLog.e("error CSSNodeModel json " + json);
            return false;
        }
        return applyCssLayout(vg, model);
    }

    public static boolean applyCssLayout(ViewGroup vg, CSSNodeModel nodeModel) {
        CSSNode node = CSSNodeUtil.getNode(vg);
        if (node != null) {
            return nodeModel != null ? doUpdateCssLayout(vg, nodeModel) : resetCssLayout(vg);
        } else {
            return nodeModel != null ? doApplyCssLayout(vg, nodeModel) : false;
        }
    }

    private static boolean doApplyCssLayout(ViewGroup vg, CSSNodeModel nodeModel) {
        if (!canApplyCssLayout(vg, nodeModel)) {
            return false;
        }

        return recurseBuildCssNodeTree(vg, nodeModel, null) != null;
    }

    private static boolean doUpdateCssLayout(ViewGroup vg, CSSNodeModel nodeModel) {
        if (!canUpdateCssLayout(vg, nodeModel)) {
            return false;
        }
        recurseUpdateCssLayout(vg, nodeModel, null);
        return true;
    }

    private static void recurseUpdateCssLayout(View view, CSSNodeModel cssModel, StubCSSLayout parentCSSLayout) {
        CSSNode node = CSSNodeFactory.newNode(cssModel);
        CSSNodeUtil.bind(view, node);

        ViewGroup vg = (view instanceof ViewGroup) ? (ViewGroup) view : null;
        if (vg == null || vg.getChildCount() == 0) {
            if (parentCSSLayout != null) {
                CSSNodeUtil.getNode(parentCSSLayout).addChild(node);
            }
            return;
        }

        StubCSSLayout cssLayout = (StubCSSLayout) vg.getChildAt(0);
        List<View> children = ViewUtil.getChildren(cssLayout);

        // vg add cssLayout
        CSSNodeUtil.bind(cssLayout, node);
        int count = children.size();
        for (int i=0; i<count; i++) {
            View child = children.get(i);
            CSSNodeModel childModel = cssModel.children.get(i);
            recurseUpdateCssLayout(child, childModel, cssLayout);
        }

        return;
    }

    private static boolean canUpdateCssLayout(ViewGroup vg, CSSNodeModel nodeModel) {
        if (nodeModel == null || vg == null || vg.getChildCount() > 1) {
            return false;
        }

        if (vg.getChildCount() == 0) {
            return true;
        }

        View child = vg.getChildAt(0);
        if (!(child instanceof StubCSSLayout)) {
            return false;
        }

        StubCSSLayout cssLayout = (StubCSSLayout) child;
        List<View> children = ViewUtil.getChildren(cssLayout);
        if (nodeModel.children.size() != children.size()) {
            return false;
        }

        int count = children.size();
        for (int i=0; i<count; i++) {
            View v = children.get(i);
            CSSNodeModel subModel = nodeModel.children.get(i);

            ViewGroup grandChildren = (v instanceof ViewGroup) ? (ViewGroup) v : null;
            if (grandChildren != null && !canUpdateCssLayout(grandChildren, subModel)) {
                return false;
            }
        }

        return true;
    }

    public static boolean resetCssLayout(ViewGroup vg) {
        if (!canReverseCssLayout(vg)) {
            return false;
        }

        StubCSSLayout cssLayout = (StubCSSLayout) vg.getChildAt(0);
        doResetCssLayout(vg, cssLayout);

        return true;
    }

    private static void doResetCssLayout(ViewGroup vg, StubCSSLayout cssLayout) {
        // 恢复 padding
        vg.setPadding(cssLayout.getPaddingLeft(),
                cssLayout.getPaddingTop(),
                cssLayout.getPaddingRight(),
                cssLayout.getPaddingBottom());
        vg.removeView(cssLayout);
        CSSNode cssNode = CSSNodeUtil.getNode(vg);
        CSSNodeUtil.unbindNode(cssLayout);
        CSSNodeUtil.unbindNode(vg);
        CSSNodeUtil.free(cssNode);

        List<View> children = ViewUtil.getChildren(cssLayout);
        cssLayout.removeAllViews();

        for (View child : children) {
            CSSNodeUtil.unbindNode(child);
            LayoutStore.restoreViewLayout(child);

            CSSNode childCssNode = CSSNodeUtil.getNode(vg);
            CSSNodeUtil.free(childCssNode);

            vg.addView(child);

            ViewGroup vgChild = (child instanceof ViewGroup) ? (ViewGroup) child : null;
            if (vgChild != null && vgChild.getChildCount() > 0) {
                StubCSSLayout childCssLayout = (StubCSSLayout) vgChild.getChildAt(0);
                doResetCssLayout(vgChild, childCssLayout);
            }
        }
    }

    private static boolean canReverseCssLayout(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() != 1) {
            return false;
        }

        View child = viewGroup.getChildAt(0);
        if (!(child instanceof StubCSSLayout)) {
            return false;
        }

        StubCSSLayout cssLayout = (StubCSSLayout) child;
        int count = cssLayout.getChildCount();
        for (int i=0; i<count; i++) {
            View grantChild = cssLayout.getChildAt(i);

            ViewGroup vg = (grantChild instanceof ViewGroup) ? (ViewGroup) grantChild : null;
            if (vg == null || vg.getChildCount() == 0) {
                continue;
            }

            if (!canReverseCssLayout(vg)) {
                return false;
            }
        }

        return true;
    }

    private static StubCSSLayout recurseBuildCssNodeTree(View view, CSSNodeModel cssModel,
                                                         StubCSSLayout parentCSSLayout) {
        CSSNode node = CSSNodeFactory.newNode(cssModel);
        LayoutStore.storeViewLayout(view);

        ViewGroup vg = (view instanceof ViewGroup) ? (ViewGroup) view : null;
        if (vg == null || vg.getChildCount() == 0) {
            CSSNodeUtil.bind(view, node);

            if (parentCSSLayout != null) {
                parentCSSLayout.addView(view);
                CSSNodeUtil.getNode(parentCSSLayout).addChild(node);
            }
            return null;
        }

        List<View> children = new LinkedList<View>();
        int count = vg.getChildCount();
        for (int i=0; i<count; i++) {
            View child = vg.getChildAt(i);
            children.add(child);
        }

        StubCSSLayout cssLayout = new StubCSSLayout(vg.getContext());
        // vg add cssLayout
        cssLayout.bindContainer(vg, node);
        if (parentCSSLayout != null) {
            int index = ViewUtil.removeFromParent(vg);
            parentCSSLayout.addView(vg, index);
        }

        for (int i=0; i<count; i++) {
            View child = children.get(i);
            vg.removeView(child);

            CSSNodeModel childModel = cssModel.children.get(i);
            recurseBuildCssNodeTree(child, childModel, cssLayout);
        }

        return cssLayout;
    }

    private static boolean canApplyCssLayout(View view, CSSNodeModel cssNode) {
        if (cssNode == null || view == null) {
            return false;
        }

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            int nodeCount = cssNode.children != null ? cssNode.children.size() : 0;
            // 没有子 view，就当做普通 view，没必要排版
            if (count == 0 || count != nodeCount) {
                return false;
            }

            for (int i=0; i<count; i++) {
                View child = vg.getChildAt(i);
                CSSNodeModel cssChild = cssNode.children.get(i);
                if (!canApplyCssLayout(child, cssChild)) {
                    return false;
                }
            }
        }

        return true;
    }
}
