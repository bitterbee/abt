package com.netease.libs.abtestbase.layout.csslayout;

import android.view.View;
import android.view.ViewGroup;

import com.netease.libs.abtestbase.R;

/**
 * Created by zyl06 on 2018/9/2.
 */
public class LayoutStore {

    public static void storeViewLayout(View v) {
        LayoutProp prop = new LayoutProp();

//        ViewGroup.LayoutParams lp = v.getLayoutParams();
//        if (lp instanceof ViewGroup.MarginLayoutParams) {
//            ViewGroup.MarginLayoutParams marginLParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
//            prop.leftMargin = marginLParams.leftMargin;
//            prop.topMargin = marginLParams.topMargin;
//            prop.rightMargin = marginLParams.rightMargin;
//            prop.bottomMargin = marginLParams.bottomMargin;
//        }
//
//        if (lp != null) {
//            prop.layoutWidth = lp.width;
//            prop.layoutHeight = lp.height;
//        } else {
//            prop.layoutWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
//            prop.layoutHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
//        }

        prop.layoutParams = v.getLayoutParams();

        v.setTag(R.string.csslayout_nodeview_prop_tag, prop);
    }

    public static void restoreViewLayout(View v) {
        LayoutProp prop = (LayoutProp) v.getTag(R.string.csslayout_nodeview_prop_tag);
        if (prop != null) {
            v.setLayoutParams(prop.layoutParams);
        }
    }

    private static class LayoutProp {
        public int leftMargin;
        public int topMargin;
        public int rightMargin;
        public int bottomMargin;

        public int layoutWidth;
        public int layoutHeight;

        ViewGroup.LayoutParams layoutParams;
    }
}
