package com.netease.tools.abtestuicreator.view.prop.concret;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.netease.libs.abtestbase.ABTestResUtil;
import com.netease.libs.abtestbase.model.ABTextSizeModel;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;
import com.netease.tools.abtestuicreator.view.prop.ViewPropAnno;

/**
 * Created by zyl06 on 2018/8/3.
 */
@ViewPropAnno(viewType = TextView.class, name = UIProp.PROP_TEXT_SIZE)
public class TextSizeEditPropView extends EditPropView<String> {

    private float mOldTextSize;

    public TextSizeEditPropView(Context context) {
        this(context, null);
    }

    public TextSizeEditPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextSizeEditPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        if (v instanceof TextView) {
            TextView tv = (TextView) v;

            String str = value.toString();
            try {
                ABTextSizeModel model = ABTestResUtil.parseTextSize(str);
                if (model != null) {
                    tv.setTextSize(model.unit, model.size);
                    mNewValue = str;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (v instanceof TextView) {
            ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_PX, mOldTextSize);
        }
    }

    @Override
    protected void onBindView(View v) {
        if (v instanceof TextView) {
            mOldTextSize = ((TextView) v).getTextSize();
            mValue.setText(mOldTextSize + "px");
        }
    }
}
