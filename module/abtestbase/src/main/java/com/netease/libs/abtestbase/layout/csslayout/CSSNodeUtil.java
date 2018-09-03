package com.netease.libs.abtestbase.layout.csslayout;

import android.view.View;

import com.facebook.csslayout.CSSNode;
import com.netease.libs.abtestbase.R;

import java.util.LinkedList;

/**
 * Created by zyl06 on 2018/8/31.
 */

public class CSSNodeUtil {

    private static final LinkedList<CSSNode> NODE_POOL = new LinkedList<CSSNode>();

    public static synchronized CSSNode obtain() {
        if (!NODE_POOL.isEmpty()) {
            CSSNode node = NODE_POOL.removeLast();
            return node;
        }
        return new CSSNode();
    }

    public static synchronized void free(CSSNode node) {
        if (node == null || NODE_POOL.contains(node)) {
            return;
        }
        int count = node.getChildCount();
        for (int i=0; i<count; i++) {
            CSSNode child = node.getChildAt(i);
            free(child);
        }
        node.resetChildren();
        node.reset();
        NODE_POOL.addLast(node);
    }

    public static void bind(View view, CSSNode cssNode) {
        if (view != null && cssNode != null) {
            view.setTag(R.string.css_node_tag, cssNode);
        }
    }

    public static CSSNode getNode(View view) {
        return view != null ? (CSSNode) view.getTag(R.string.css_node_tag) : null;
    }

    public static void unbindNode(View view) {
        if (view != null) {
            view.setTag(R.string.css_node_tag, null);
        }
    }
}
