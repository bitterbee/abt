package com.netease.demo.abtest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneABTester test1 = new OneABTester();
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(test1.getName());
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