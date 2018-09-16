package com.netease.libs.abtestbase;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.netease.libs.abtestbase.model.ABTestUICase;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/26.
 */

public class JsonUtil {
    private static final Gson GSON = new Gson();

    public static String toJSONString(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T parse(String json, Class<T> type) {
        try {
            if (!TextUtils.isEmpty(json)) {
                return GSON.fromJson(json, type);
            }
        } catch (JsonSyntaxException e) {
            ABLog.e(e);
        }

        return null;
    }

    public static <T> List<T> parseArray(String json, Class<T> type) {
        try {
            if (!TextUtils.isEmpty(json)) {
                return GSON.fromJson(json, TypeToken.getParameterized(List.class, type).getType());
            }
        } catch (JsonSyntaxException e) {
            ABLog.e(e);
        }

        return null;
    }
}
