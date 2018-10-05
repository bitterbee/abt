package com.netease.demo.abtest.prop;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.abtest.uiprop.UIPropSetterAnno;
import com.netease.lib.abtest.ui.prop.IPropSetter;
import com.netease.libs.abtestbase.model.UIProp;

/**
 * Created by zyl06 on 2018/9/25.
 */
@UIPropSetterAnno()
public class FrescoSrcPropSetter implements IPropSetter {

    @Override
    public boolean apply(View view, UIProp prop) {
        if (prop.value instanceof String) {
            Uri uri = Uri.parse((String) prop.value);
            ((SimpleDraweeView) view).setImageURI(uri);

            return true;
        }

        return false;
    }

    @Override
    public String name() {
        return "fresco_src";
    }
}
