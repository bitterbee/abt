package com.netease.lib.abtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.netease.lib.abtest.model.ABTestItem;
import com.netease.libs.abtestbase.ABTestContext;
import com.netease.libs.abtestbase.JsonUtil;
import com.netease.libs.abtestbase.model.ABTestUICase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/10/21.
 */

public class ABConfigStore {

    private static SharedPreferences sSharedPreference = null;

    public static final String KEY_NORMAL_CASES = "KEY_NORMAL_CASES";
    public static final String KEY_UI_CASES = "KEY_UI_CASES";

    public static void updateABNormalCases(String json) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString(KEY_NORMAL_CASES, json);
        editor.apply();
    }

    public static void updateABUICases(String json) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString(KEY_UI_CASES, json);
        editor.apply();
    }

    public static List<ABTestItem> readABNormalCases() {
        String json = getSharePreference().getString(KEY_NORMAL_CASES, null);
        return TextUtils.isEmpty(json) ?
                new ArrayList<ABTestItem>() :
                JsonUtil.parseArray(json, ABTestItem.class);
    }

    public static List<ABTestUICase> readABUICases() {
        String json = getSharePreference().getString(KEY_UI_CASES, null);
        return TextUtils.isEmpty(json) ?
                new ArrayList<ABTestUICase>() :
                JsonUtil.parseArray(json, ABTestUICase.class);
    }

    private static SharedPreferences getSharePreference() {
        if (sSharedPreference == null) {
            sSharedPreference = ABTestContext.getContext().getSharedPreferences("ABTestStore", Context.MODE_PRIVATE);
        }
        return sSharedPreference;
    }
}
