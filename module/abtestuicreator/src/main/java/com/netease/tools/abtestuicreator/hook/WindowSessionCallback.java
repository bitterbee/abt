package com.netease.tools.abtestuicreator.hook;

import android.view.View;

/**
 * Created by zyl06 on 2018/7/29.
 */

public interface WindowSessionCallback {
    /**
     * 主要是这个方法，这个方法在ViewRootImpl类的setView方法中被调用，用来与WindowManagerService远程通信
     * @param decorView 分两种，一种是DecorView，一种是PopupDecorView
     */
    void onAddToDisplay(View decorView);

    void onAdd(View decorView);

    void onAddWithoutInputChannel(View decorView);

    void onAddToDisplayWithoutInputChannel(View decorView);
}