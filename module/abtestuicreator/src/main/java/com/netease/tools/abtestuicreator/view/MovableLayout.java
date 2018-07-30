package com.netease.tools.abtestuicreator.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class MovableLayout extends LinearLayout implements View.OnLongClickListener, View.OnClickListener {
    private IViewDragCallback mDraggedCallback;
    private float mDownX, mDownY;
    private boolean mIsMovable = false;

    private CheckBox mCheckBox;

    public MovableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnLongClickListener(this);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
//            ViewUtil.markIgnore(getChildAt(i));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        mIsMovable = true;
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getRawX() - mDownX;
                float dy = event.getRawY() - mDownY;
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                if (mIsMovable && mDraggedCallback != null) {
                    mDraggedCallback.onDragged(dx, dy);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsMovable = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setViewDraggedCallback(IViewDragCallback callback) {
        mDraggedCallback = callback;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        if (mCheckBox == null) {
            mCheckBox = (CheckBox) getChildAt(1);
        }
        mCheckBox.setOnCheckedChangeListener(listener);
    }

    public void setChecked(boolean checked) {
        if (mCheckBox == null) {
            mCheckBox = (CheckBox) getChildAt(1);
        }
        mCheckBox.setChecked(checked);
    }
}
