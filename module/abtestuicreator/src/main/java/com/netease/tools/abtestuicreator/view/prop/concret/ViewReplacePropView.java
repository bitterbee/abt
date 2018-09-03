package com.netease.tools.abtestuicreator.view.prop.concret;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.ViewUtil;
import com.netease.libs.abtestbase.layout.DynamicLayoutInflater;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;

import java.lang.ref.WeakReference;

/**
 * Created by zyl06 on 2018/8/12.
 */
@UIPropCreatorAnno(viewType = View.class, name = UIProp.PROP_REPLACE_XML_DYLAYOUT)
public class ViewReplacePropView extends EditPropView<String> {

    private DynamicLayoutInflater mLayoutInflater;
    private View mOldView;

    public ViewReplacePropView(Context context) {
        this(context, null);
    }

    public ViewReplacePropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewReplacePropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ViewReplacePropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        String str = value.toString();
        try {
            mLayoutInflater = new DynamicLayoutInflater(v.getContext());
            if (!mLayoutInflater.isValid()) {
                return;
            }

            View newView = mLayoutInflater.inflate(str, (ViewGroup) v.getParent(), false);
            if (newView != null) {
                mViewRef = new WeakReference<View>(newView);
                ViewUtil.replace(v, newView);

                mNewValue = str;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mOldView != v) {
            ViewUtil.replace(v, mOldView);
            mViewRef = new WeakReference<View>(mOldView);
        }
    }

    @Override
    protected void onBindView(View v) {
        mOldView = v;
        String xml = (String) v.getTag(com.netease.libs.abtestbase.R.string.replace_xml_dylayout_tag);
        if (xml != null) {
            mValue.setText(xml);
        }
    }
}
