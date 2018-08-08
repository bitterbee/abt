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

import com.netease.libs.abtestbase.ABTestFileUtil;
import com.netease.libs.abtestbase.ViewPathUtil;
import com.netease.libs.abtestbase.anno.ABTestIgnore;
import com.netease.libs.abtestbase.model.ABTestUICase;
import com.netease.libs.abtestbase.model.UIProp;
import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.util.ABTestSpUtil;
import com.netease.tools.abtestuicreator.util.ViewUtil;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;
import com.netease.tools.abtestuicreator.view.prop.ViewPropMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/30.
 */
@ABTestIgnore
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

        RenderView renderView = (RenderView) findViewById(R.id.view_render);
        renderView.bindView(sView);

        EditPropView vId = (EditPropView) findViewById(R.id.attr_id);
        vId.setValue(ViewUtil.getIdName(sView));

        EditPropView vPath = (EditPropView) findViewById(R.id.attr_path);
        mViewPath = ViewPathUtil.getViewPath(sView);
        vPath.setValue(mViewPath);

        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        mAttrsContainer = (LinearLayout) findViewById(R.id.ll_attr);
        Collection<EditPropView> editViews = ViewPropMap.getEditPropViews(this, sView);
        int minHeight = getResources().getDimensionPixelSize(R.dimen.abtest_attr_view_h);
        for (EditPropView v : editViews) {
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
        ABTestUICase uiCase = new ABTestUICase();
        uiCase.setUiProps(new ArrayList<UIProp>());

        int count = mAttrsContainer.getChildCount();
        for (int i=0; i<count; i++) {
            EditPropView view = (EditPropView) mAttrsContainer.getChildAt(i);
            if (view.getId() == R.id.attr_path) {
                uiCase.setViewPath(view.getValue());
            } else {
                Object value = view.getNewValue();
                if (value != null) {
                    UIProp prop = new UIProp();
                    prop.name = view.getName();

                    if (value instanceof Integer) {
                        prop.intValue = (int) value;
                    } else if (value instanceof Float) {
                        prop.floatValue = (float) value;
                    } else {
                        prop.value = value;
                    }

                    uiCase.getUiProps().add(prop);
                }
            }
        }

        ABTestSpUtil.put(this, mViewPath, uiCase);
        List<ABTestUICase> allData = ABTestSpUtil.getAll(this);
        ABTestFileUtil.writeUiCases(this, allData);

        finish();
    }

    private void onCancelFinish() {
        ABTestSpUtil.remove(this, mViewPath);
        int count = mAttrsContainer.getChildCount();
        for (int i=0; i<count; i++) {
            EditPropView view = (EditPropView) mAttrsContainer.getChildAt(i);
            view.restoreValue();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
