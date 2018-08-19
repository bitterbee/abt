package com.netease.lib.abtest.ui.prop;

import android.view.View;
import android.view.ViewGroup;

import com.netease.libs.abtestbase.layout.DynamicLayoutInflater;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/12.
 */
public class ViewReplaceSetter implements IPropSetter {

    private static DynamicLayoutInflater sLayoutInflater;

    @Override
    public boolean apply(View view, UIProp prop) {

        if (sLayoutInflater == null) {
            sLayoutInflater = new DynamicLayoutInflater(view.getContext());
        }
        if (!sLayoutInflater.isValid()) {
            return false;
        }

        ViewGroup vg = (ViewGroup) view.getParent();
        if (vg == null || !(prop.value instanceof String)) {
            return false;
        }

        String encodeXml = (String) prop.value;
        View replaceView = sLayoutInflater.inflate(encodeXml, vg, false);
        if (replaceView == null) {
            return false;
        }

        int index = -1;
        int childCount = vg.getChildCount();
        for (int i=0; i<childCount; i++) {
            if (vg.getChildAt(i) == view) {
                index = i;
            }
        }

        vg.addView(replaceView, index);
        vg.removeView(view);
        return true;
    }

    @Override
    public String name() {
        return UIProp.PROP_REPLACE_XML_DYLAYOUT;
    }
}
