package com.netease.demo.abtest.forth;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.demo.abtest.R;

/**
 * Created by zyl06 on 2018/9/25.
 */

public class ForthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);

        SimpleDraweeView sdv = (SimpleDraweeView) findViewById(R.id.sdv_image);

//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/master/docs/static/logo.png");
//        sdv.setImageURI(uri);
    }
}
