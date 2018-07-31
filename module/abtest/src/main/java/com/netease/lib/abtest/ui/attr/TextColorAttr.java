package com.netease.lib.abtest.ui.attr;

import android.view.View;
import android.widget.TextView;

import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class TextColorAttr implements AttrSetter {

    @Override
    public void apply(View view, UIProp prop) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            ABLog.i("TextColorAttr");
            tv.setTextColor(prop.intValue);
        }
    }
}
