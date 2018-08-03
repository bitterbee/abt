package com.netease.tools.abtestuicreator.view.prop.concret;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.netease.libs.abtestbase.ABTestResUtil;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;
import com.netease.tools.abtestuicreator.view.prop.ViewPropAnno;

/**
 * Created by zyl06 on 2018/8/3.
 */
@ViewPropAnno(viewType = ImageView.class, name = UIProp.PROP_IMAGE_SRC)
public class ImageSrcPropView extends EditPropView<String> {

    private Drawable mDrawable;

    public ImageSrcPropView(Context context) {
        this(context, null);
    }

    public ImageSrcPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSrcPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageSrcPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        String str = value.toString();
        try {
            int resId = ABTestResUtil.getId(v.getContext(), str);
            if (resId != ABTestResUtil.NO_RES) {
                mNewValue = str;
                ((ImageView) v).setImageResource(resId);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mDrawable != null) {
            ((ImageView) v).setImageDrawable(mDrawable);
        }
    }

    @Override
    protected void onBindView(View v) {
        mDrawable = ((ImageView) v).getDrawable();
    }
}
