package com.netease.demo.abtest.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.demo.abtest.R;

/**
 * Created by zyl06 on 2018/9/23.
 */

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        View text1 = findViewById(R.id.text1);
        ViewGroup vg = (ViewGroup) text1.getParent();

        View insert = getLayoutInflater().inflate(R.layout.view_third_insert, vg, false);
        vg.addView(insert, 2);

        TextView insertText = new TextView(this);
        insertText.setText("InsertText");
        insertText.setBackgroundResource(R.color.green);
        vg.addView(insertText, 3, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        vg.removeView(text1);
    }
}
