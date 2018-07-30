package com.netease.lib.abtest.util;

import com.google.gson.Gson;

/**
 * Created by zyl06 on 2018/7/26.
 */

public class JsonUtil {
    private static final Gson GSON = new Gson();

    public static String toJSONString(Object obj) {
        return GSON.toJson(obj);
    }
}
