package com.netease.tools.abtestuicreator.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.netease.tools.abtestuicreator.notify.EventTypes;
import com.netease.tools.abtestuicreator.notify.NotifyMgr;
import com.netease.tools.abtestuicreator.notify.ISubscriber;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class RenderView extends View implements ISubscriber, View.OnLayoutChangeListener {

    private View mView;

    public RenderView(Context context) {
        super(context);
    }

    public RenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bindView(View v) {
        this.mView = v;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mView != null) {
            mView.draw(canvas);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mView.addOnLayoutChangeListener(this);
        NotifyMgr.getInstance().register(this, EventTypes.REDRAW_EVENT);
        NotifyMgr.getInstance().register(this, EventTypes.REFRESH_EVENT);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mView.removeOnLayoutChangeListener(this);
        NotifyMgr.getInstance().unregister(this);
    }

    @Override
    public void onReceiveEvent(int type, Object event) {
        switch (type) {
            case EventTypes.REDRAW_EVENT:
                postInvalidate();
                break;
            case EventTypes.REFRESH_EVENT:
                if (mView != null) {
                    mView.requestLayout();
                    postInvalidate();
                }
                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (v == mView) {
            postInvalidate();
        }
    }
}
