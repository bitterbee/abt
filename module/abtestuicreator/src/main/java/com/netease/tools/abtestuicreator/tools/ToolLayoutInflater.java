package com.netease.tools.abtestuicreator.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.netease.libs.abtestbase.ProxyLayoutInflater;
import com.netease.libs.abtestbase.anno.ABTestIgnore;
import com.netease.tools.abtestuicreator.util.LongClickReplaceUtil;
import com.netease.tools.abtestuicreator.view.SwitchManager;

/**
 * Created by zyl06 on 2018/8/4.
 */
public class ToolLayoutInflater extends ProxyLayoutInflater {

    public ToolLayoutInflater(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public LayoutInflater cloneInContext(Context context) {
        LayoutInflater inflater = mInflater.cloneInContext(context);
        return new ToolLayoutInflater(inflater);
    }

    @Override
    protected void onInflate(View created) {
        Context context = created.getContext();
        if (context != null && context.getClass().getAnnotation(ABTestIgnore.class) != null) {
            return;
        }

        if (SwitchManager.getInstance().isOpen()) {
            LongClickReplaceUtil.performTraversal(created, true);
        }
        SwitchManager.getInstance().register(created);
    }
}