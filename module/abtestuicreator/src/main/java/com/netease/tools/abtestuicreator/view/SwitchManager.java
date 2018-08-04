package com.netease.tools.abtestuicreator.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.util.LongClickReplaceUtil;
import com.netease.tools.abtestuicreator.util.ScreenUtil;
import com.netease.tools.abtestuicreator.util.ViewUtil;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/30.
 * from xue
 */

public class SwitchManager {

    private static SwitchManager sInstance = null;
    public static SwitchManager getInstance() {
        if (sInstance == null) {
            synchronized (SwitchManager.class) {
                if (sInstance == null) {
                    sInstance = new SwitchManager();
                }
            }
        }
        return sInstance;
    }

    private SwitchManager() {
    }

    private boolean mIsOpen = false;

    private float mX = 0;
    private float mY = 0;

    public MovableLayout newSwitchButton(final ViewGroup root, Context context) {

        if (mY == 0) {
            mY = ScreenUtil.getStatusBarHeight(context);
        }
        final MovableLayout switchButton = (MovableLayout) LayoutInflater.from(
                context.getApplicationContext()).
                inflate(R.layout.abtest_view_switch, root, false);
        ViewUtil.markIgnore(switchButton);
        switchButton.setTranslationX(mX);
        switchButton.setTranslationY(mY);
        switchButton.setChecked(mIsOpen);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsOpen = isChecked;
                updateAllViews(isChecked);
            }
        });
        switchButton.setViewDraggedCallback(new IViewDragCallback() {
            @Override
            public void onDragged(float dx, float dy) {
                mX += dx;
                mY += dy;
                mX = Math.max(Math.min(mX,
                        root.getMeasuredWidth() - switchButton.getMeasuredWidth()), 0);
                mY = Math.max(Math.min(mY,
                        root.getMeasuredHeight() - switchButton.getMeasuredHeight()),
                        ScreenUtil.getStatusBarHeight(root.getContext()));
                switchButton.setTranslationX(mX);
                switchButton.setTranslationY(mY);
            }
        });
        return switchButton;
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    private List<WeakReference<View>> mViewRefs = new LinkedList<>();
    public void register(View rootView) {
        if (rootView == null || rootView.getContext() == null) {
            return;
        }
        if (rootView.getContext() instanceof Activity) {
            if (((Activity) rootView.getContext()).isFinishing()) {
                return;
            }
        }

        mViewRefs.add(new WeakReference<View>(rootView));
    }

    public synchronized void updateAllViews(boolean replace) {
        List<WeakReference<View>> deadViewRefs = new LinkedList<>();
        for (WeakReference<View> viewRef : mViewRefs) {
            View v = viewRef.get();
            if (v == null) {
                deadViewRefs.add(viewRef);
                continue;
            }

            LongClickReplaceUtil.performTraversal(v, replace);
        }

        mViewRefs.removeAll(deadViewRefs);
    }
}
