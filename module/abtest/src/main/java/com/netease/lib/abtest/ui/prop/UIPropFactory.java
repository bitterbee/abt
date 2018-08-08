package com.netease.lib.abtest.ui.prop;

import android.text.TextUtils;

import com.netease.abtest.uiprop.UIPropSetterAnno;
import com.netease.libs.abtestbase.RefInvoker;
import com.netease.libs.abtestbase.model.UIProp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/29.
 */
public class UIPropFactory {

    private static final Map<String, IPropSetter> UIPROP_SETTERS = new HashMap<>();

    public IPropSetter getPropSetter(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }

        if (UIPROP_SETTERS.isEmpty()) {
            // View
            UIPROP_SETTERS.put(UIProp.PROP_BG, new BgPropSetter());
            UIPROP_SETTERS.put(UIProp.PROP_ALPHA, new AlphaPropSetter());

            // TextView
            UIPROP_SETTERS.put(UIProp.PROP_TEXT_COLOR, new TextColorPropSetter());
            UIPROP_SETTERS.put(UIProp.PROP_TEXT_STRING, new TextStringPropSetter());
            UIPROP_SETTERS.put(UIProp.PROP_TEXT_SIZE, new TextSizePropSetter());

            // ImageView
            UIPROP_SETTERS.put(UIProp.PROP_IMAGE_SRC, new ImageSrcPropSetter());

//            Object customTable = RefInvoker.newInstance("com.netease.libs.abtest.ABTestUIPropTable", )
            Map<Object, UIPropSetterAnno> customSetters = (Map<Object, UIPropSetterAnno>) RefInvoker.invokeStaticMethod("com.netease.libs.abtest.ABTestUIPropTable",
                    "getUIPropSetters", null, null);
            if (customSetters != null) {
                for (Map.Entry<Object, UIPropSetterAnno> entry : customSetters.entrySet()) {
                    if (entry.getKey() instanceof IPropSetter) {
                        IPropSetter setter = (IPropSetter) entry.getKey();
                        UIPROP_SETTERS.put(setter.name(), setter);
                    }
                }
            }
        }

        return UIPROP_SETTERS.get(name);
    }
}
