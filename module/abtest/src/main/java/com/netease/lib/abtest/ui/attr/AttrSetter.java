package com.netease.lib.abtest.ui.attr;

import android.view.View;

import com.netease.lib.abtest.model.UIProp;

/**
 * Created by zyl06 on 2018/7/29.
 */

public interface AttrSetter {

    /**
     * Use to apply view with new TypedValue
     * @param view
     */
    void apply(View view, UIProp prop);
}
