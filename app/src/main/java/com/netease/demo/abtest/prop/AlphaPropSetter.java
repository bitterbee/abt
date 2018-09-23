package com.netease.demo.abtest.prop;

import android.view.View;

import com.netease.lib.abtest.ui.prop.IPropSetter;
import com.netease.libs.abtestbase.model.UIProp;

import com.netease.abtest.uiprop.UIPropSetterAnno;

/**
 * Created by zyl06 on 2018/8/3.
 */
@UIPropSetterAnno()
public class AlphaPropSetter implements IPropSetter {

    @Override
    public boolean apply(View view, UIProp prop) {
        if (!(prop.value instanceof Float)) {
            return false;
        }

        float value = (float) prop.value;
        if (value >= 0 && value <= 1) {
            view.setAlpha(value);
            return true;
        }

        return false;
    }

    @Override
    public String name() {
        return "alpha";
    }
}
