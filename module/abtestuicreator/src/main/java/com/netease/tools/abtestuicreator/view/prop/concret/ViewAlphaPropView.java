package com.netease.tools.abtestuicreator.view.prop.concret;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;

/**
 * Created by zyl06 on 2018/8/3.
 */
@UIPropCreatorAnno(viewType = View.class, name = UIProp.PROP_ALPHA)
public class ViewAlphaPropView extends EditPropView<Float> {

    private float mOldAlpha;

    public ViewAlphaPropView(Context context) {
        this(context, null);
    }

    public ViewAlphaPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewAlphaPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewAlphaPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        v.setAlpha(mOldAlpha);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        try {
            mNewValue = Float.parseFloat(value.toString());
            v.setAlpha(mNewValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBindView(View v) {
        mOldAlpha = v.getAlpha();
        mValue.setText(Float.toString(mOldAlpha));
    }
}
