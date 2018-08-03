package com.netease.lib.abtest.ui.prop;

import android.text.TextUtils;

import com.netease.libs.abtestbase.model.UIProp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/29.
 */
public class UIPropFactory {

    private static final Map<String, PropSetter> ATTR_MAP = new HashMap<String, PropSetter>() {
        {
            put(UIProp.PROP_BG, new BgPropSetter()); //
            put(UIProp.PROP_TEXT_COLOR, new TextColorPropSetter());
            put(UIProp.PROP_TEXT_STRING, new TextStringPropSetter());

            put(UIProp.PROP_IMAGE_SRC, new ImageSrcPropSetter()); //
            put(UIProp.PROP_TEXT_SIZE, new TextSizePropSetter()); //
            put(UIProp.PROP_ALPHA, new AlphaPropSetter()); //
        }
    };

    public PropSetter getPropSetter(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return ATTR_MAP.get(name);
    }
}
