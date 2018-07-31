package com.netease.tools.abtestuicreator.tools;

import android.view.View;

import com.netease.tools.abtestuicreator.view.ABTestEditActivity;

/**
 * Created by zyl06 on 2018/7/30.
 */
public class ReplaceLongClickListenerImpl implements View.OnLongClickListener {

    private View.OnLongClickListener mOriLongClickListener;

    public ReplaceLongClickListenerImpl(View.OnLongClickListener oriLongClickListener) {
        this.mOriLongClickListener = oriLongClickListener;
    }

    public View.OnLongClickListener getOriLongClickListener() {
        return mOriLongClickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        ABTestEditActivity.start(v.getContext(), v);
        return false;
    }
}
