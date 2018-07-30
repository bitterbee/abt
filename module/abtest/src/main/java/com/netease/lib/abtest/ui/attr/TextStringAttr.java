package com.netease.lib.abtest.ui.attr;

import android.view.View;
import android.widget.TextView;

import com.netease.lib.abtest.model.UIProp;
import com.netease.lib.abtest.util.ABLog;

/**
 * Created by zyl06 on 2018/7/29.
 */
public class TextStringAttr implements AttrSetter {

    @Override
    public void apply(View view, UIProp prop) {
        if (view instanceof TextView && prop.attrValue instanceof CharSequence) {
            TextView tv = (TextView) view;

            ABLog.i("TextStringAttr");
            tv.setText((CharSequence) prop.attrValue);
        }
    }
}
