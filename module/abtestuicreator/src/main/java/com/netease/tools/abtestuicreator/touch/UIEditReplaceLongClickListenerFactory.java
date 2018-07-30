package com.netease.tools.abtestuicreator.touch;

import android.view.View;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class UIEditReplaceLongClickListenerFactory implements IReplaceLongClickListenerFactory {

    @Override
    public View.OnLongClickListener build(View v) {
        return new UIEditReplaceLongClickListenerProxy(v);
    }

    private class UIEditReplaceLongClickListenerProxy implements View.OnLongClickListener {
        private ViewInfoPopupWindow mPopupWindow;
        private View mDecorView;

        public UIEditReplaceLongClickListenerProxy(View decorView) {
            this.mDecorView = decorView;
            this.mPopupWindow = new ViewInfoPopupWindow(decorView);
        }

        @Override
        public boolean onLongClick(View v) {
            mPopupWindow.showViewInfo(mDecorView, v);
            return true;
        }
    }
}
