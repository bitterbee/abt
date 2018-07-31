package com.netease.lib.abtest.ui.attr;

import android.text.TextUtils;

import com.netease.libs.abtestbase.model.UIProp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class UIAttrFactory {

    private static final Map<String, AttrSetter> ATTR_MAP = new HashMap<String, AttrSetter>() {
        {
            put(UIProp.PROP_TEXT_COLOR, new TextColorAttr());
            put(UIProp.PROP_TEXT_STRING, new TextStringAttr());
        }
    };

    public AttrSetter getAttr(String attrName) {
        if (TextUtils.isEmpty(attrName)) {
            return null;
        }
        return ATTR_MAP.get(attrName);
    }
}
