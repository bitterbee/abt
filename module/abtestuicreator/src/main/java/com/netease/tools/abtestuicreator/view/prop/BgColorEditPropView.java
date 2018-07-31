package com.netease.tools.abtestuicreator.view.prop;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.util.ColorUtil;

/**
 * Created by zyl06 on 2018/7/30.
 */
public class BgColorEditPropView extends EditPropView<Integer> {

    private Drawable mBgDrawable;

    public BgColorEditPropView(Context context) {
        this(context, null);
    }

    public BgColorEditPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BgColorEditPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BgColorEditPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mName.setText(UIProp.PROP_BG_COLOR);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        String str = value.toString();
        try {
            mNewValue = Color.parseColor(str);
            v.setBackgroundColor(mNewValue);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mBgDrawable != null) {
            v.setBackground(mBgDrawable);
        }
    }

    @Override
    protected void onBindView(View v) {
        mBgDrawable = v.getBackground();
        if (mBgDrawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) mBgDrawable).getColor();
            mValue.setText(ColorUtil.ColorToHex(color));
        }
    }
}
