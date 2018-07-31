package com.netease.libs.abtestbase;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by zyl06 on 2018/7/26.
 */

public class JsonUtil {
    private static final Gson GSON = new Gson();

    public static String toJSONString(Object obj) {
        return GSON.toJson(obj);
    }

    public static Map parseMap(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return GSON.fromJson(json, Map.class);
    }
}
