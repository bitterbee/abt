package com.netease.lib.abtest.ui.prop;

import android.view.View;

import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/3.
 */
public class AlphaPropSetter implements IPropSetter {
    @Override
    public boolean apply(View view, UIProp prop) {
        if (prop.value == null && prop.floatValue >= 0 && prop.floatValue <= 1) {
            view.setAlpha(prop.floatValue);
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return UIProp.PROP_ALPHA;
    }
}
