package com.netease.tools.abtestuicreator.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.netease.libs.abtestbase.ViewPathUtil;
import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.util.ABTestSpUtil;
import com.netease.tools.abtestuicreator.util.ViewUtil;
import com.netease.tools.abtestuicreator.view.attr.EditAttrView;
import com.netease.tools.abtestuicreator.view.attr.ViewAttrMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ABTestEditActivity extends AppCompatActivity implements View.OnClickListener {

    private static View sView;

    private String mViewPath;
    private LinearLayout mAttrsContainer;

    public static boolean start(Context context, View v) {
        if (context == null || v == null) {
            return false;
        }

        sView = v;

        Intent i = new Intent(context, ABTestEditActivity.class);
        if (!(context instanceof Activity)) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(i);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abtest_activity_edit);

        RenderView renderView = findViewById(R.id.view_render);
        renderView.bindView(sView);

        EditAttrView vId = findViewById(R.id.attr_id);
        vId.setValue(ViewUtil.getIdName(sView));

        EditAttrView vPath = findViewById(R.id.attr_path);
        mViewPath = ViewPathUtil.getViewPath(sView);
        vPath.setValue(mViewPath);

        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        mAttrsContainer = findViewById(R.id.ll_attr);
        List<EditAttrView> editViews = ViewAttrMap.getEditAttrViews(this, sView);
        int minHeight = getResources().getDimensionPixelSize(R.dimen.abtest_attr_view_h);
        for (EditAttrView v : editViews) {
            v.bindView(sView);
            mAttrsContainer.addView(v, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mAttrsContainer.setMinimumHeight(minHeight);

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_confirm) {
            onConfirmFinish();
        } else if (id == R.id.btn_cancel) {
            onCancelFinish();
        }
    }

    private void onConfirmFinish() {

        Map<String, Object> data = new HashMap<>();
        int count = mAttrsContainer.getChildCount();
        for (int i=0; i<count; i++) {
            EditAttrView view = (EditAttrView) mAttrsContainer.getChildAt(i);
            Object value = view.getNewValue();
            if (value != null) {
                data.put(view.getName(), view.getNewValue());
            }
        }

        ABTestSpUtil.put(this, mViewPath, data);
        ABTestSpUtil.saveToDisk(this);

        finish();
    }

    private void onCancelFinish() {
        ABTestSpUtil.remove(this, mViewPath);
        int count = mAttrsContainer.getChildCount();
        for (int i=0; i<count; i++) {
            EditAttrView view = (EditAttrView) mAttrsContainer.getChildAt(i);
            view.restoreValue();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
