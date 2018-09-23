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
    public boolean apply(View view, UIProp prop) {
        ABLog.i("BgPropSetter");
        if (prop.value instanceof Integer) {
            view.setBackgroundColor((int) prop.value);
            return true;
        } else if (prop.value instanceof String) {
            int id = ABTestResUtil.getId(view.getContext(), (String) prop.value);
            if (id != ABTestResUtil.NO_RES) {
                view.setBackgroundResource(id);
                return true;
            }
        }

        return false;
    }

    @Override
    public String name() {
        return UIProp.PROP_BG;
    }
}
