package com.netease.lib.abtest.ui.prop;

import android.view.View;

import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.ABTestResUtil;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/3.
 */

public class BgPropSetter implements IPropSetter {

    @Override
    public void apply(View view, UIProp prop) {
        ABLog.i("BgPropSetter");
        if (prop.intValue != 0) {
            view.setBackgroundColor(prop.intValue);
        } else if (prop.value instanceof String) {
            int id = ABTestResUtil.getId(view.getContext(), (String) prop.value);
            if (id != ABTestResUtil.NO_RES) {
                view.setBackgroundResource(id);
            }
        }
    }

    @Override
    public String name() {
        return UIProp.PROP_BG;
    }
}
