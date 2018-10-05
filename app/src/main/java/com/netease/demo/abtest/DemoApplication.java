package com.netease.demo.abtest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.netease.lib.abtest.ABTestConfig;
import com.netease.lib.abtest.model.ABTestItem;
import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.ABTestFileUtil;
import com.netease.libs.abtestbase.JsonUtil;
import com.netease.tools.abtestuicreator.ABTestMonitor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ABTestMonitor.getInstance().init(this);

        List<ABTestItem> testItems = parseJsonFromAsset();
        ABTestConfig.getInstance().init(this, testItems, ABTestFileUtil.readUiCases(this));
        Fresco.initialize(this);
    }

    private List<ABTestItem> parseJsonFromAsset() {
        List<ABTestItem> result = null;
        InputStream is = null;
        try {
            is = getAssets().open("abtest.json");
            String json = from(is, "UTF-8");
            result = JsonUtil.parseArray(json, ABTestItem.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result != null ? result : new ArrayList<ABTestItem>();
    }

    public static String from(InputStream is, String charset) {
        if (is == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            int bufSize = 128;
            byte[] buf = new byte[bufSize];
            int count = -1;
            while ((count = is.read(buf, 0, bufSize)) > 0) {
                os.write(buf, 0, count);
            }
            os.flush();
            return new String(os.toByteArray(), charset);
        } catch (IOException e) {
            ABLog.e(e.toString());
        } catch (Exception e) {
            ABLog.e(e.toString());
        } finally {
            try {
                is.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
