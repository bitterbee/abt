package com.netease.demo.abtest;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by zyl06 on 2018/8/4.
 */

public class PopupWindowView {
    protected Context mContext;
    private PopupWindow mPopupWindow;
    private FrameLayout mContentView;
    private boolean mIsOutsideTouchable = true;
    private OutsideTouchListener mTouchListener;

    public PopupWindowView(Context context) {
        this(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
    }

    public PopupWindowView(Context context, int gravity) {
        this(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                gravity);
    }

    public PopupWindowView(Context context, int width, int height, int gravity) {
        this.mContext = context;

        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.popupwindow_view_center, null);
        view.setBackgroundResource(R.color.popupwindow_mask);
        view.setGravity(gravity);
        mContentView = (FrameLayout) view.findViewById(R.id.content_view);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        lp.width = width;
        lp.height = height;
        lp.gravity = gravity;
        mContentView.setLayoutParams(lp);

        view.findViewById(R.id.ll_mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsOutsideTouchable) {
                    if (mTouchListener != null)
                        mTouchListener.outsideTouchListener();
                    else dismiss();
                }
            }
        });

        mPopupWindow = createPopupWindow(view);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setClippingEnabled(false);//允许弹出窗口超出屏幕范围
        mPopupWindow.setAnimationStyle(R.style.popWindowAnimBottom);//设置默认渐隐动画
    }

    protected PopupWindow createPopupWindow(View rootView){
        return new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void addSubView(View view, FrameLayout.LayoutParams lp) {
        mContentView.addView(view, lp);
        //view是显示内容的,设置onTouch返回true,点击在范围内的事件不会触发dismiss
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public ViewGroup getContentView() {
        return mContentView;
    }

    /**
     * 添加动画
     */
    public void setAnimationStyle(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
    }

    //下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent, int xOffset, int yOffset) {
        //保证尺寸是根据屏幕像素密度来的
        mPopupWindow.showAsDropDown(parent, xOffset, yOffset);
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        //刷新状态
        mPopupWindow.update();
    }

    /**
     * 显示在父View正中央
     */
    public void showInCenter(View parent) {
        //保证尺寸是根据屏幕像素密度来的
        mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        //刷新状态
        mPopupWindow.update();
    }

    public int getHeight() {
        return mPopupWindow.getHeight();
    }

    public void setHeight(int height) {
        mPopupWindow.setHeight(height);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        showAtLocation(parent, gravity, x, y, false);
    }

    /**
     * 默认不在底部弹出
     *
     * @param parent
     * @param gravity
     * @param x
     * @param y
     * @param isOutsideTouchable
     */
    public void showAtLocation(View parent, int gravity, int x, int y, boolean isOutsideTouchable) {
        showAtLocation(parent, gravity, x, y, isOutsideTouchable, false);
    }

    /**
     * 如在底部弹出,最后一个参数传true,方法内部做判断是否有虚拟导航栏做上移调整
     *
     * @param parent
     * @param gravity
     * @param x
     * @param y
     * @param isOutsideTouchable
     * @param isShowBottom
     */
    public void showAtLocation(View parent, int gravity, int x, int y, boolean isOutsideTouchable, boolean isShowBottom) {
        //根据是否有虚拟导航栏判断位置
        //
        /*if (parent!=null && isShowBottom && SystemUtil.checkDeviceHasNavigationBar(parent.getContext())) {
            if (SystemUtil.getBuildVersionSDK()>=24){
                mPopupWindow.showAtLocation(parent,gravity, x, y-Math.abs(SystemUtil.getNavigationBarHeight(parent.getContext())));
            }else {
                mPopupWindow.showAtLocation(parent,gravity, x, y+Math.abs(SystemUtil.getNavigationBarHeight(parent.getContext())));

            }
        }else {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }*/
        if(isShowBottom){
            mPopupWindow.setClippingEnabled(true);
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(isOutsideTouchable);
        mIsOutsideTouchable = isOutsideTouchable;
        //刷新状态
        mPopupWindow.update();
        mPopupWindow.showAtLocation(parent, gravity, x, y);

    }

    public void setFocusable(boolean focusable) {
        mPopupWindow.setFocusable(focusable);
        mPopupWindow.setOutsideTouchable(focusable);
        mPopupWindow.update();
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public void dismiss() {
        if (isShowing())
            mPopupWindow.dismiss();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mPopupWindow.setOnDismissListener(listener);
    }

    public void setTouchListener(OutsideTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    public void setDismissListener(PopupWindow.OnDismissListener dismissListener) {
        mPopupWindow.setOnDismissListener(dismissListener);
    }

    /**
     * 设置背景的颜色
     * 2.1.0地址选择控件特殊需求
     *
     * @param color 背景颜色
     */
    public void setContentViewBackgroundColor(int color) {
        if (mPopupWindow != null && mPopupWindow.getContentView().findViewById(R.id.ll_mask) != null)
            mPopupWindow.getContentView().findViewById(R.id.ll_mask).setBackgroundColor(color);
    }

    public interface OutsideTouchListener {
        void outsideTouchListener();
    }
}
