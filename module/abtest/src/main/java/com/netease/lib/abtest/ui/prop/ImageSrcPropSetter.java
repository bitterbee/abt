package com.netease.lib.abtest.ui.prop;

import android.view.View;
import android.widget.ImageView;

import com.netease.libs.abtestbase.ABTestResUtil;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/8/3.
 */

public class ImageSrcPropSetter implements IPropSetter {

    @Override
    public void apply(View view, UIProp prop) {
        if (view instanceof ImageView && prop.value instanceof String) {
            String name = (String) prop.value;
            int id = ABTestResUtil.getId(view.getContext(), name);
            if (id != ABTestResUtil.NO_RES) {
                ((ImageView) view).setImageResource(id);
            }
        }
    }

    @Override
    public String name() {
        return UIProp.PROP_IMAGE_SRC;
    }
}
