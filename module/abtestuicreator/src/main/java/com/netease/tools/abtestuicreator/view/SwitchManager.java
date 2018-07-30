package com.netease.tools.abtestuicreator.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.util.ScreenUtil;
import com.netease.tools.abtestuicreator.util.ViewUtil;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class SwitchManager {

    private static class Singleton {
        static SwitchManager INSTANCE = new SwitchManager();
    }

    public static SwitchManager getInstance() {
        return Singleton.INSTANCE;
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

}
