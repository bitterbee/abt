package com.netease.tools.abtestuicreator.view.prop.concret;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;

/**
 * Created by zyl06 on 2018/7/30.
 */
@UIPropCreatorAnno(viewType = TextView.class, name = UIProp.PROP_TEXT_STRING)
public class TextStringEditPropView extends EditPropView<String> {

    private CharSequence mOriText;

    public TextStringEditPropView(Context context) {
        this(context, null);
    }

    public TextStringEditPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextStringEditPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextStringEditPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mOriText != null && v instanceof TextView) {
            ((TextView) v).setText(mOriText);
        }
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        if (v instanceof TextView) {
            mNewValue = value.toString();
            ((TextView) v).setText(mNewValue);
        }
    }

    @Override
    protected void onBindView(View v) {
        if (v instanceof TextView) {
            mOriText = ((TextView) v).getText();
            mValue.setText(mOriText);
        }
    }
}
