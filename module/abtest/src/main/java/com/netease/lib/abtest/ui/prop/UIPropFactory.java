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
            put(UIProp.PROP_TEXT_COLOR, new TextColorPropSetter());
            put(UIProp.PROP_TEXT_STRING, new TextStringPropSetter());
        }
    };

    public PropSetter getPropSetter(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return ATTR_MAP.get(name);
    }
}
