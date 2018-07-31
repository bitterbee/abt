package com.netease.tools.abtestuicreator.view.attr;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.util.ColorUtil;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class TextColorEditAttrView extends EditAttrView<Integer> {

    private @ColorInt Integer mOriTextColor;

    public TextColorEditAttrView(Context context) {
        this(context, null);
    }

    public TextColorEditAttrView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextColorEditAttrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextColorEditAttrView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mName.setText(UIProp.PROP_TEXT_COLOR);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        if (v instanceof TextView) {
            TextView tv = (TextView) v;

            String str = value.toString();
            try {
                mNewValue = Color.parseColor(str);
                tv.setTextColor(mNewValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mOriTextColor != null && v instanceof TextView) {
            ((TextView) v).setTextColor(mOriTextColor);
        }
    }

    @Override
    protected void onBindView(View v) {
        if (v instanceof TextView) {
            mOriTextColor = ((TextView) v).getCurrentTextColor();
            mValue.setText(ColorUtil.ColorToHex(mOriTextColor));
        }
    }
}