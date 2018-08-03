package com.netease.lib.abtest.ui.prop;

import android.view.View;
import android.widget.TextView;

import com.netease.libs.abtestbase.ABTestResUtil;
import com.netease.libs.abtestbase.model.ABTextSizeModel;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/3.
 */

public class TextSizePropSetter implements PropSetter {

    @Override
    public void apply(View view, UIProp prop) {
        if (view instanceof TextView && prop.value instanceof String) {
            ABTextSizeModel model = ABTestResUtil.parseTextSize((String) prop.value);
            if (model != null) {
                ((TextView) view).setTextSize(model.unit, model.size);
            }
        }
    }
}
