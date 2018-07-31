package com.netease.tools.abtestuicreator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.netease.lib.abtest.util.CollectionUtil;
import com.netease.libs.abtestbase.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ABTestSpUtil {

    public static final String SP_NAME = "ABTestUICreator";
    public static synchronized void put(Context context, String path, Map<String, ?> jsonObj) {
        if (CollectionUtil.isEmpty(jsonObj)) {
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(path, JsonUtil.toJSONString(jsonObj));
        editor.apply();
    }

    public static synchronized void remove(Context context, String path) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(path);
        editor.apply();
    }

    public static synchronized List<Map<String, ?>> getAll(Context context) {
        List<Map<String, ?>> result = new ArrayList<>();

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        for (Map.Entry<String, ?> entry : sp.getAll().entrySet()) {
            String json = (String) entry.getValue();
            Map map = JsonUtil.parseMap(json);
            if (map != null) {
                result.add(map);
            }
        }

        return result;
    }

    public static synchronized boolean saveToDisk(Context context) {
        List<Map<String, ?>> all = getAll(context);
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        File file = new File(downloadDir, "abtest_ui.txt");
        try {
            FileUtil.writeToFile(file.getAbsolutePath(), JsonUtil.toJSONString(all));
            return true;
        } catch (IOException e) {
            LogUtil.e(e.toString());
        }

        return false;
    }
}
