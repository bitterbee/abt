package com.netease.demo.abtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eyeofcloud.Eyeofcloud;
import com.netease.demo.abtest.forth.ForthActivity;
import com.netease.demo.abtest.second.SecondActivity;
import com.netease.demo.abtest.third.ThirdActivity;
import com.netease.libs.abtestbase.layout.DynamicLayoutInflater;

public class MainActivity extends AppCompatActivity {

    private DynamicLayoutInflater dynamicLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneABTester test1 = new OneABTester();
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(test1.getName());

        Eyeofcloud.enableEditor(); //注意：正式发布的App中一定 不要 调用此方法！
        Eyeofcloud.startEyeofcloudWithAPIToken("3bc64afd0f9a1745563507c353aa542a~1714",getApplication());
    }




//    private void testCustomLayout() {
//
//        if (dynamicLayoutInflater == null) {
//            dynamicLayoutInflater = new DynamicLayoutInflater(this);
//            if (!dynamicLayoutInflater.isValid()) {
//                return;
//            }
//        }
//
//        View dyView = null;
//        try {
//            String content = "AwAIALAHAAABABwA/AIAABkAAAAAAAAAAAAAAIAAAAAAAAAAAAAAABwAAAA6AAAAUgAAAGwAAAB0AAAAjgAAAKoAAADKAAAA1AAAAPIAAAAEAQAAEAEAACYBAAA6AQAAUAEAAGIBAAC6AQAAvgEAANoBAAD0AQAACAIAADYCAABIAgAAXAIAAAwAbABhAHkAbwB1AHQAXwB3AGkAZAB0AGgAAAANAGwAYQB5AG8AdQB0AF8AaABlAGkAZwBoAHQAAAAKAGIAYQBjAGsAZwByAG8AdQBuAGQAAAALAG8AcgBpAGUAbgB0AGEAdABpAG8AbgAAAAIAaQBkAAAACwBwAGEAZABkAGkAbgBnAEwAZQBmAHQAAAAMAHAAYQBkAGQAaQBuAGcAUgBpAGcAaAB0AAAADgBsAGEAeQBvAHUAdABfAGcAcgBhAHYAaQB0AHkAAAADAHQAYQBnAAAADQBsAGEAeQBvAHUAdABfAHcAZQBpAGcAaAB0AAAABwBnAHIAYQB2AGkAdAB5AAAABAB0AGUAeAB0AAAACQB0AGUAeAB0AEMAbwBsAG8AcgAAAAgAdABlAHgAdABTAGkAegBlAAAACQB0AGUAeAB0AFMAdAB5AGwAZQAAAAcAYQBuAGQAcgBvAGkAZAAAACoAaAB0AHQAcAA6AC8ALwBzAGMAaABlAG0AYQBzAC4AYQBuAGQAcgBvAGkAZAAuAGMAbwBtAC8AYQBwAGsALwByAGUAcwAvAGEAbgBkAHIAbwBpAGQAAAAAAAAADABMAGkAbgBlAGEAcgBMAGEAeQBvAHUAdAAAAAsARgByAGEAbQBlAEwAYQB5AG8AdQB0AAAACABUAGUAeAB0AFYAaQBlAHcAAAAVAFIALgBpAGQALgB0AHYAXwBhAGwAZQByAHQAXwBjAG8AbgB0AGUAbgB0AAAABwAyADAAMQA4AHReNQAIZwAACAB0AGEAZwBfAGQAYQB0AGEAAAANAFIALgBpAGQALgB0AHYAXwByAGkAZwBoAHQAAAAAAIABCABEAAAA9AABAfUAAQHUAAEBxAABAdAAAQHWAAEB2AABAbMAAQHRAAEBgQEBAa8AAQFPAQEBmAABAZUAAQGXAAEBAAEQABgAAAACAAAA/////w8AAAAQAAAAAgEQAHQAAAACAAAA//////////8SAAAAFAAUAAQAAAAAAAAAEAAAAAMAAAD/////CAAAEAAAAAAQAAAAAgAAAP////8IAAAd+vr6/xAAAAAAAAAA/////wgAABD/////EAAAAAEAAAD/////CAAABQEvAAACARAAiAAAAAgAAAD//////////xMAAAAUABQABQAAAAAAAAAQAAAABAAAAP////8IAAABAAADfxAAAAAFAAAA/////wgAAAUBEgAAEAAAAAYAAAD/////CAAABQESAAAQAAAAAAAAAP////8IAAAQ/v///xAAAAABAAAA/////wgAABD/////AgEQAJwAAAAPAAAA//////////8UAAAAFAAUAAYAAAAAAAAAEAAAAAcAAAD/////CAAAEREAAAAQAAAABAAAAP////8IAAABAQADfxAAAAAIAAAAFQAAAAgAAAMVAAAAEAAAAAIAAAD/////CAAAHYjQPP8QAAAAAAAAAP////8IAAAFAQcAABAAAAABAAAA/////wgAAAUhAEAGAwEQABgAAAAVAAAA//////////8UAAAAAwEQABgAAAAWAAAA//////////8TAAAAAgEQAOwAAAAYAAAA//////////8UAAAAFAAUAAoAAAAAAAAAEAAAAA0AAAD/////CAAABQEQAAAQAAAADgAAAP////8IAAARAQAAABAAAAAMAAAA/////wgAAB0zMzP/EAAAAAoAAAD/////CAAAEREAAAAQAAAABAAAAP////8IAAABAgADfxAAAAAIAAAAFwAAAAgAAAMXAAAAEAAAAAAAAAD/////CAAABQEAAAAQAAAAAQAAAP////8IAAAQ/////xAAAAALAAAAFgAAAAgAAAMWAAAAEAAAAAkAAAD/////CAAABAAAgD8DARAAGAAAACIAAAD//////////xQAAAACARAAiAAAACQAAAD//////////xMAAAAUABQABQAAAAAAAAAQAAAABAAAAP////8IAAABAwADfxAAAAAFAAAA/////wgAAAUBEgAAEAAAAAYAAAD/////CAAABQESAAAQAAAAAAAAAP////8IAAAQ/v///xAAAAABAAAA/////wgAABD/////AgEQAJwAAAArAAAA//////////8UAAAAFAAUAAYAAAAAAAAAEAAAAAcAAAD/////CAAAEREAAAAQAAAABAAAAP////8IAAABBAADfxAAAAAIAAAAGAAAAAgAAAMYAAAAEAAAAAIAAAD/////CAAAHYjQPP8QAAAAAAAAAP////8IAAAFAQcAABAAAAABAAAA/////wgAAAUhAEAGAwEQABgAAAAxAAAA//////////8UAAAAAwEQABgAAAAyAAAA//////////8TAAAAAwEQABgAAAA0AAAA//////////8SAAAAAQEQABgAAAA0AAAA/////w8AAAAQAAAA";
//
//            dyView = dynamicLayoutInflater.inflate(content, (ViewGroup) findViewById(R.id.replace_stub), true);
//
//            TextView tv = (TextView) dyView.findViewWithTag("tag_data");
//            if (tv != null) {
//                tv.setText("modify text view");
//            }
//
//            tv = dyView.findViewById(R.id.tv_alert_content);
//            if (tv != null) {
//                tv.setText("alert_content");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void onShowDialog(View v) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();

//        View contentView = getLayoutInflater().inflate(R.layout.dlg_demo0, null);
//        contentView.findViewById(R.id.btn_alert_negative).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        contentView.findViewById(R.id.btn_alert_positive).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.setContentView(R.layout.dlg_demo0);
    }

    public void onShowPopupWindow(View v) {
        PopupWindowView popup = new PopupWindowView(this);
        View subview = getLayoutInflater().inflate(R.layout.popupwindow_demo0, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        popup.addSubView(subview, lp);
        popup.showInCenter(findViewById(R.id.ll_content));
    }

    public void onGotoSecondActivity(View v) {
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }

    public void onGotoThirdActivity(View v) {
        Intent i = new Intent(this, ThirdActivity.class);
        startActivity(i);
    }

    public void onGotoForthActivity(View v) {
        Intent i = new Intent(this, ForthActivity.class);
        startActivity(i);
    }
}