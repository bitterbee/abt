package com.netease.lib.abtest.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.netease.libs.abtestbase.ProxyLayoutInflater;

/**
 * Created by zyl06 on 2018/8/4.
 */
class ABTestProxyLayoutInflater extends ProxyLayoutInflater {

    public ABTestProxyLayoutInflater(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public LayoutInflater cloneInContext(Context context) {
        LayoutInflater inflater = mInflater.cloneInContext(context);
        return new ABTestProxyLayoutInflater(inflater);
    }

    @Override
    protected void onInflate(View created) {
        UIPropSetterMgr.applyView(created);
    }
}