package com.netease.demo.abtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

/**
 * Created by zyl06 on 2018/8/22.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        WebView view = findViewById(R.id.webview);



        view.loadUrl("http://10.242.53.10:8000/jumpapp.html");
    }
}
