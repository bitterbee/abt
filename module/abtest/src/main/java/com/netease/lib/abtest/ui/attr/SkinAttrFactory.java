package com.netease.lib.abtest.ui.attr;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class SkinAttrFactory {
    private static final String RES_TYPE_NAME_TEXT_COLOR = "color";
    private static final String RES_TYPE_NAME_TEXT_STRING = "string";
    private static final String RES_TYPE_NAME_DRAWABLE = "drawable";

    private static final Map<String, AttrSetter> ATTR_MAP = new HashMap<String, AttrSetter>() {
        {
            put("text_color", new TextColorAttr());
            put("text_string", new TextStringAttr());
        }
    };

    public AttrSetter getAttr(String attrName) {
        if (TextUtils.isEmpty(attrName)) {
            return null;
        }
        return ATTR_MAP.get(attrName);
    }
}
