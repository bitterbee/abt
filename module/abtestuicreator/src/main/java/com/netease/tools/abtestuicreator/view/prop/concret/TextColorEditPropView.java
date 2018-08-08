package com.netease.tools.abtestuicreator.view.prop.concret;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.util.ColorUtil;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;

/**
 * Created by zyl06 on 2018/7/30.
 */
@UIPropCreatorAnno(viewType = TextView.class, name = UIProp.PROP_TEXT_COLOR)
public class TextColorEditPropView extends EditPropView<Integer> {

    private @ColorInt Integer mOriTextColor;

    public TextColorEditPropView(Context context) {
        this(context, null);
    }

    public TextColorEditPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextColorEditPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextColorEditPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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