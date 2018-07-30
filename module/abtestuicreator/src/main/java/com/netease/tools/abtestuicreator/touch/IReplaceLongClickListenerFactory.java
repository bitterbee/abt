package com.netease.tools.abtestuicreator.touch;

import android.view.View;

/**
 * Created by zyl06 on 2018/7/29.
 */

public interface IReplaceLongClickListenerFactory {
    View.OnLongClickListener build(View v);
}
