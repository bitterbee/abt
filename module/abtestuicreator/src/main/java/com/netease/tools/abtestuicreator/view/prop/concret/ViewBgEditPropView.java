package com.netease.tools.abtestuicreator.view.prop.concret;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import com.netease.libs.abtestbase.ABTestResUtil;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.util.ColorUtil;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;
import com.netease.tools.abtestuicreator.view.prop.ViewPropAnno;

/**
 * Created by zyl06 on 2018/7/30.
 */
@ViewPropAnno(name = UIProp.PROP_BG)
public class ViewBgEditPropView extends EditPropView {

    private Drawable mBgDrawable;

    public ViewBgEditPropView(Context context) {
        this(context, null);
    }

    public ViewBgEditPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewBgEditPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewBgEditPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        String str = value.toString();
        try {
            if (str.startsWith("#")) {
                mNewValue = Color.parseColor(str);
                v.setBackgroundColor((int) mNewValue);
            } else {
                int resId = ABTestResUtil.getId(v.getContext(), str);
                if (resId != ABTestResUtil.NO_RES) {
                    mNewValue = str;
                    v.setBackgroundResource(resId);
                }
            }

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
