package com.netease.demo.abtest;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.netease.lib.abtest.ABTestConfig;
import com.netease.lib.abtest.model.ABTestItem;
import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.ABTestFileUtil;
import com.netease.libs.abtestbase.JsonUtil;
import com.netease.libs.abtestbase.layout.DynamicLayoutInflater;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DynamicLayoutInflater dynamicLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ABTestItem> testItems = parseJsonFromAsset();
        ABTestConfig.getInstance().init(this.getApplication(), testItems, ABTestFileUtil.readUiCases(this));

        OneABTester test1 = new OneABTester();
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(test1.getName());
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


    private void testCustomLayout() {

        if (dynamicLayoutInflater == null) {
            dynamicLayoutInflater = new DynamicLayoutInflater(this);
            if (!dynamicLayoutInflater.isValid()) {
                return;
            }
        }

        View dyView = null;
        try {
//            AssetManager am = getAssets();
//            InputStream is = am.open("test_layout.xml");
////            InputStream is = am.open("test_custom_layout_2.bin");
//
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            byte[] slice = new byte[1024];
//            int count;
//            while ((count = is.read(slice)) > 0) {
//                os.write(slice, 0, count);
//            }
//
//            String content = CryptoUtil.base64Encode(os.toByteArray());
            String content = "AwAIALAHAAABABwA/AIAABkAAAAAAAAAAAAAAIAAAAAAAAAAAAAAABwAAAA6AAAAUgAAAGwAAAB0AAAAjgAAAKoAAADKAAAA1AAAAPIAAAAEAQAAEAEAACYBAAA6AQAAUAEAAGIBAAC6AQAAvgEAANoBAAD0AQAACAIAADYCAABIAgAAXAIAAAwAbABhAHkAbwB1AHQAXwB3AGkAZAB0AGgAAAANAGwAYQB5AG8AdQB0AF8AaABlAGkAZwBoAHQAAAAKAGIAYQBjAGsAZwByAG8AdQBuAGQAAAALAG8AcgBpAGUAbgB0AGEAdABpAG8AbgAAAAIAaQBkAAAACwBwAGEAZABkAGkAbgBnAEwAZQBmAHQAAAAMAHAAYQBkAGQAaQBuAGcAUgBpAGcAaAB0AAAADgBsAGEAeQBvAHUAdABfAGcAcgBhAHYAaQB0AHkAAAADAHQAYQBnAAAADQBsAGEAeQBvAHUAdABfAHcAZQBpAGcAaAB0AAAABwBnAHIAYQB2AGkAdAB5AAAABAB0AGUAeAB0AAAACQB0AGUAeAB0AEMAbwBsAG8AcgAAAAgAdABlAHgAdABTAGkAegBlAAAACQB0AGUAeAB0AFMAdAB5AGwAZQAAAAcAYQBuAGQAcgBvAGkAZAAAACoAaAB0AHQAcAA6AC8ALwBzAGMAaABlAG0AYQBzAC4AYQBuAGQAcgBvAGkAZAAuAGMAbwBtAC8AYQBwAGsALwByAGUAcwAvAGEAbgBkAHIAbwBpAGQAAAAAAAAADABMAGkAbgBlAGEAcgBMAGEAeQBvAHUAdAAAAAsARgByAGEAbQBlAEwAYQB5AG8AdQB0AAAACABUAGUAeAB0AFYAaQBlAHcAAAAVAFIALgBpAGQALgB0AHYAXwBhAGwAZQByAHQAXwBjAG8AbgB0AGUAbgB0AAAABwAyADAAMQA4AHReNQAIZwAACAB0AGEAZwBfAGQAYQB0AGEAAAANAFIALgBpAGQALgB0AHYAXwByAGkAZwBoAHQAAAAAAIABCABEAAAA9AABAfUAAQHUAAEBxAABAdAAAQHWAAEB2AABAbMAAQHRAAEBgQEBAa8AAQFPAQEBmAABAZUAAQGXAAEBAAEQABgAAAACAAAA/////w8AAAAQAAAAAgEQAHQAAAACAAAA//////////8SAAAAFAAUAAQAAAAAAAAAEAAAAAMAAAD/////CAAAEAAAAAAQAAAAAgAAAP////8IAAAd+vr6/xAAAAAAAAAA/////wgAABD/////EAAAAAEAAAD/////CAAABQEvAAACARAAiAAAAAgAAAD//////////xMAAAAUABQABQAAAAAAAAAQAAAABAAAAP////8IAAABAAADfxAAAAAFAAAA/////wgAAAUBEgAAEAAAAAYAAAD/////CAAABQESAAAQAAAAAAAAAP////8IAAAQ/v///xAAAAABAAAA/////wgAABD/////AgEQAJwAAAAPAAAA//////////8UAAAAFAAUAAYAAAAAAAAAEAAAAAcAAAD/////CAAAEREAAAAQAAAABAAAAP////8IAAABAQADfxAAAAAIAAAAFQAAAAgAAAMVAAAAEAAAAAIAAAD/////CAAAHYjQPP8QAAAAAAAAAP////8IAAAFAQcAABAAAAABAAAA/////wgAAAUhAEAGAwEQABgAAAAVAAAA//////////8UAAAAAwEQABgAAAAWAAAA//////////8TAAAAAgEQAOwAAAAYAAAA//////////8UAAAAFAAUAAoAAAAAAAAAEAAAAA0AAAD/////CAAABQEQAAAQAAAADgAAAP////8IAAARAQAAABAAAAAMAAAA/////wgAAB0zMzP/EAAAAAoAAAD/////CAAAEREAAAAQAAAABAAAAP////8IAAABAgADfxAAAAAIAAAAFwAAAAgAAAMXAAAAEAAAAAAAAAD/////CAAABQEAAAAQAAAAAQAAAP////8IAAAQ/////xAAAAALAAAAFgAAAAgAAAMWAAAAEAAAAAkAAAD/////CAAABAAAgD8DARAAGAAAACIAAAD//////////xQAAAACARAAiAAAACQAAAD//////////xMAAAAUABQABQAAAAAAAAAQAAAABAAAAP////8IAAABAwADfxAAAAAFAAAA/////wgAAAUBEgAAEAAAAAYAAAD/////CAAABQESAAAQAAAAAAAAAP////8IAAAQ/v///xAAAAABAAAA/////wgAABD/////AgEQAJwAAAArAAAA//////////8UAAAAFAAUAAYAAAAAAAAAEAAAAAcAAAD/////CAAAEREAAAAQAAAABAAAAP////8IAAABBAADfxAAAAAIAAAAGAAAAAgAAAMYAAAAEAAAAAIAAAD/////CAAAHYjQPP8QAAAAAAAAAP////8IAAAFAQcAABAAAAABAAAA/////wgAAAUhAEAGAwEQABgAAAAxAAAA//////////8UAAAAAwEQABgAAAAyAAAA//////////8TAAAAAwEQABgAAAA0AAAA//////////8SAAAAAQEQABgAAAA0AAAA/////w8AAAAQAAAA";

            dyView = dynamicLayoutInflater.inflate(content, (ViewGroup) findViewById(R.id.replace_stub), true);

            TextView tv = (TextView) dyView.findViewWithTag("tag_data");
            if (tv != null) {
                tv.setText("modify text view");
            }

            tv = dyView.findViewById(R.id.tv_alert_content);
            if (tv != null) {
                tv.setText("alert_content");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (dyView == null) {
//            throw new InflateException("test");
//        }
    }

    public void onShowDialog(View v) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();

        View contentView = getLayoutInflater().inflate(R.layout.dlg_demo0, null);
        contentView.findViewById(R.id.btn_alert_negative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        contentView.findViewById(R.id.btn_alert_positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
    }

    public void onShowPopupWindow(View v) {
        PopupWindowView popup = new PopupWindowView(this);
        View subview = getLayoutInflater().inflate(R.layout.popupwindow_demo0, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        popup.addSubView(subview, lp);
        popup.showInCenter(findViewById(R.id.ll_content));
    }
}