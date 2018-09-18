package com.netease.libs.abtestbase.layout.csslayout;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.csslayout.CSSNode;
import com.netease.libs.abtestbase.R;

import java.util.LinkedList;

/**
 * Created by zyl06 on 2018/8/31.
 */
public class CSSLayoutUtil {

    private static final LinkedList<CSSNode> NODE_POOL = new LinkedList<CSSNode>();

    public static synchronized CSSNode obtainNode() {
        if (!NODE_POOL.isEmpty()) {
            CSSNode node = NODE_POOL.removeLast();
            return node;
        }
        return new CSSNode();
    }

    public static synchronized void freeNode(CSSNode node) {
        if (node == null || NODE_POOL.contains(node)) {
            return;
        }
        int count = node.getChildCount();
        for (int i=0; i<count; i++) {
            CSSNode child = node.getChildAt(i);
            freeNode(child);
        }
        node.resetChildren();
        node.reset();
        NODE_POOL.addLast(node);
    }

    public static void bindNode(View view, CSSNode cssNode) {
        if (view != null && cssNode != null) {
            view.setTag(R.string.abtest_tag_css_node, cssNode);
        }
    }

    public static CSSNode getNode(View view) {
        return view != null ? (CSSNode) view.getTag(R.string.abtest_tag_css_node) : null;
    }

    public static void unbindNode(View view) {
        if (view != null) {
            view.setTag(R.string.abtest_tag_css_node, null);
        }
    }

    public static void storeViewLayout(View v) {
        v.setTag(R.string.abtest_tag_csslayout_nodeview_prop, v.getLayoutParams());
    }

    public static void restoreViewLayout(View v) {
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) v.getTag(R.string.abtest_tag_csslayout_nodeview_prop);
        if (lp != null) {
            v.setLayoutParams(lp);
            v.setTag(R.string.abtest_tag_csslayout_nodeview_prop, null);
        }
    }
}
