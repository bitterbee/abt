package com.netease.tools.abtestuicreator.view.attr;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.netease.tools.abtestuicreator.R;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class TextStringEditAttrView extends EditAttrView<String> {

    private CharSequence mOriText;

    public TextStringEditAttrView(Context context) {
        this(context, null);
    }

    public TextStringEditAttrView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextStringEditAttrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextStringEditAttrView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mName.setText(R.string.abtest_text_key);
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
