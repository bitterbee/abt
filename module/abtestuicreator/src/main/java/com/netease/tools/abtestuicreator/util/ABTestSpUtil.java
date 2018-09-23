package com.netease.tools.abtestuicreator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.netease.lib.abtest.util.CollectionUtil;
import com.netease.libs.abtestbase.JsonUtil;
import com.netease.libs.abtestbase.model.ABTestUICase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ABTestSpUtil {

    public static final String SP_NAME = "ABTestUICreator";
    public static synchronized void put(Context context, String path, ABTestUICase uiCase) {
        if (uiCase == null || TextUtils.isEmpty(uiCase.getViewId()) ||
                CollectionUtil.isEmpty(uiCase.getUiProps())) {
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(path, JsonUtil.toJSONString(uiCase));
        editor.apply();
    }

    public static synchronized void remove(Context context, String path) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(path);
        editor.apply();
    }

    public static synchronized List<ABTestUICase> getAll(Context context) {
        List<ABTestUICase> result = new ArrayList<>();

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            String json = (String) entry.getValue();
            ABTestUICase uiCase = JsonUtil.parse(json, ABTestUICase.class);
            if (uiCase != null) {
                result.add(uiCase);
            }
        }

        return result;
    }
}
