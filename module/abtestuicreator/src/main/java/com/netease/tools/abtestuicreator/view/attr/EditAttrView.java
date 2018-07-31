package com.netease.tools.abtestuicreator.view.attr;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.notify.EventTypes;
import com.netease.tools.abtestuicreator.notify.NotifyMgr;

import java.lang.ref.WeakReference;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class EditAttrView<T> extends FrameLayout implements TextWatcher {

    protected TextView mName;
    protected EditText mValue;

    protected T mNewValue = null;

    protected WeakReference<View> mViewRef;

    public EditAttrView(Context context) {
        this(context, null);
    }

    public EditAttrView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditAttrView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditAttrView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.abtest_view_edit_attr, this);
        mName = (TextView) content.findViewById(R.id.tv_track_edit_attr_name);
        mValue = (EditText) content.findViewById(R.id.et_track_edit_attr_value);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditAttrView);
        String name = "null";
        String value;
        boolean isEditable = false;
        try {
            name = typedArray.getString(R.styleable.EditAttrView_name);
            value = typedArray.getString(R.styleable.EditAttrView_value);
            isEditable = typedArray.getBoolean(R.styleable.EditAttrView_editable, true);
        } finally {
            typedArray.recycle();
        }

        mName.setText(name);
        mValue.setText(value);
        mValue.setEnabled(isEditable);
        mValue.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        View v = mViewRef != null ? mViewRef.get() : null;
        if (v != null) {
            onUpdateView(v, s);
            NotifyMgr.getInstance().notify(EventTypes.REFRESH_EVENT);
        }
    }

    public void restoreValue() {
        mNewValue = null;
        View v = mViewRef != null ? mViewRef.get() : null;
        if (v != null) {
            onRestoreValue(v);
        }
    }

    // 子类实现
    protected void onRestoreValue(View v) {

    }

    // 子类实现
    protected void onUpdateView(View v, Editable value) {

    }

    // 子类实现
    protected void onBindView(View v) {

    }

    public void bindView(View v) {
        if (v != null) {
            onBindView(v);
            mViewRef = new WeakReference<View>(v);
        }
    }

    public void setValue(String value) {
        mValue.setText(value);
    }

    public String getName() {
        return mName.getText().toString();
    }

    public String getValue() {
        return mValue.getText().toString();
    }

    public Object getNewValue() {
        return mNewValue;
    }
}
