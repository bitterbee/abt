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
    public void apply(View view, UIProp prop) {
        if (prop.value == null && prop.floatValue >= 0 && prop.floatValue <= 1) {
            view.setAlpha(prop.floatValue);
        }
    }

    @Override
    public String name() {
        return "alpha";
    }
}
