package com.netease.lib.abtest.ui.prop;

import android.view.View;
import android.widget.TextView;

import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/7/29.
 */
public class TextStringPropSetter implements IPropSetter {

    @Override
    public void apply(View view, UIProp prop) {
        if (view instanceof TextView && prop.value instanceof CharSequence) {
            TextView tv = (TextView) view;

            ABLog.i("TextStringPropSetter");
            tv.setText((CharSequence) prop.value);
        }
    }

    @Override
    public String name() {
        return UIProp.PROP_TEXT_STRING;
    }
}
