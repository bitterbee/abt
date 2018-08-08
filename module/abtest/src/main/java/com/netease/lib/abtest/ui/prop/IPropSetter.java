package com.netease.lib.abtest.ui.prop;

import android.view.View;

import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/7/29.
 */

public interface IPropSetter {

    /**
     * Use to apply view with new TypedValue
     * @param view
     * @param prop
     */
    void apply(View view, UIProp prop);

    /**
     *
     * @return prop name
     */
    String name();
}
