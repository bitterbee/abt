package com.netease.lib.abtest.ui.prop;

import android.view.View;
import android.view.ViewGroup;

import com.netease.libs.abtestbase.layout.csslayout.StubCSSLayoutUtil;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/28.
 */

public class ViewCSSLayoutSetter implements IPropSetter {

    @Override
    public boolean apply(View view, UIProp prop) {
        if (view instanceof ViewGroup && prop.value instanceof String) {
            return StubCSSLayoutUtil.applyCssLayout((ViewGroup) view, (String) prop.value);
        }

        return false;
    }

    @Override
    public String name() {
        return UIProp.PROP_CSSLAYOUT;
    }

}
