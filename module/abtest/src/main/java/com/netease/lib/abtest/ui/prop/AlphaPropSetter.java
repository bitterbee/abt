package com.netease.lib.abtest.ui.prop;

import android.view.View;

import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/3.
 */
public class AlphaPropSetter implements IPropSetter {
    @Override
    public boolean apply(View view, UIProp prop) {

        float value = -1;
        if (prop.value instanceof Integer) {
            value = (int) prop.value;
        } else if (prop.value instanceof Float) {
            value = (float) prop.value;
        }

        if (value >= 0 && value <= 1) {
            view.setAlpha(value);
            return true;
        }

        return false;
    }

    @Override
    public String name() {
        return UIProp.PROP_ALPHA;
    }
}
