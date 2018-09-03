package com.netease.tools.abtestuicreator.view.prop.concret;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.layout.csslayout.StubCSSLayoutUtil;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;

/**
 * Created by zyl06 on 2018/9/1.
 */
@UIPropCreatorAnno(viewType = ViewGroup.class, name = UIProp.PROP_CSSLAYOUT)
public class ViewGroupCssLayoutPropView extends EditPropView<String> {

    private String mOldJson;

    public ViewGroupCssLayoutPropView(Context context) {
        this(context, null);
    }

    public ViewGroupCssLayoutPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroupCssLayoutPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewGroupCssLayoutPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        String str = value.toString();
        applyCssLayout(v, str);
    }

    private void applyCssLayout(View v, String json) {
        try {
            boolean success = TextUtils.isEmpty(json) ?
                    StubCSSLayoutUtil.resetCssLayout((ViewGroup) v) :
                    StubCSSLayoutUtil.applyCssLayout((ViewGroup) v, json);

            if (success) {
                v.setTag(com.netease.libs.abtestbase.R.string.css_node_json_tag, json);
                v.requestLayout();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mOldJson != null) {
            StubCSSLayoutUtil.applyCssLayout((ViewGroup) v, mOldJson);
        } else {
            StubCSSLayoutUtil.resetCssLayout((ViewGroup) v);
        }

        v.setTag(com.netease.libs.abtestbase.R.string.css_node_json_tag, mOldJson);
    }

    @Override
    protected void onBindView(View v) {
        mOldJson = (String) v.getTag(com.netease.libs.abtestbase.R.string.css_node_json_tag);
        if (mOldJson != null) {
            mValue.setText(mOldJson);
        }
    }
}
