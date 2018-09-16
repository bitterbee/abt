package com.netease.lib.abtest;

import android.app.Application;
import android.text.TextUtils;

import com.netease.lib.abtest.model.ABTestCase;
import com.netease.lib.abtest.model.ABTestConfigModel;
import com.netease.lib.abtest.model.ABTestItem;
import com.netease.lib.abtest.ui.ABTestActivityLiftcycleCallbackImpl;
import com.netease.lib.abtest.ui.UIPropSetterMgr;
import com.netease.lib.abtest.util.ObjWeakRef;
import com.netease.libs.abtestbase.JsonUtil;
import com.netease.libs.abtestbase.model.ABTestUICase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by zyl06 on 27/07/2017.
 */
public class ABTestConfig {

    private ABTestConfigModel mABTestConfigModel = new ABTestConfigModel();

    /*package*/ Set<ObjWeakRef<BaseABTester>> mABTesterRefs = new HashSet<>();

    private static ABTestConfig sInstance = null;

    public static ABTestConfig getInstance() {
        if (sInstance == null) {
            synchronized (ABTestConfig.class) {
                if (sInstance == null) {
                    sInstance = new ABTestConfig();
                }
            }
        }
        return sInstance;
    }

    private ABTestConfig() {

    }

    public void init(Application app,
                     List<ABTestItem> normalCases,
                     List<ABTestUICase> uiCases) {

        if (normalCases == null) {
            normalCases = new LinkedList<>();
        }
        if (uiCases == null) {
            uiCases = new LinkedList<>();
        }

        mABTestConfigModel.abtestConfig = normalCases;
        mABTestConfigModel.abtestUICases = uiCases;

        // 过滤 accessory == "" 的值，保证传给 h5 的 ua 无多余内容
        for (ABTestItem group : normalCases) {
            if (TextUtils.isEmpty(group.getAccessory())) {
                group.setAccessory(null);
            }

            ABTestCase testCase = group.getTestCase();
            if (TextUtils.isEmpty(testCase.getAccessory())) {
                testCase.setAccessory(null);
            }
        }

        UIPropSetterMgr.init(uiCases);
        app.registerActivityLifecycleCallbacks(new ABTestActivityLiftcycleCallbackImpl());

        notifyAllTesters();
    }

    private synchronized void notifyAllTesters() {
        for (ObjWeakRef<BaseABTester> testRef : mABTesterRefs) {
            BaseABTester tester = testRef.get();
            if (tester != null && tester.getItemId() != null) {
                ABTestItem group = getTestCase(tester.getItemId());
                if (group != null) {
                    tester.updateConfig(group);
                }
            }
        }
    }

    public ABTestItem getTestCase(String itemId) {
        if (mABTestConfigModel.abtestConfig == null) {
            return null;
        }
        for (ABTestItem group : mABTestConfigModel.abtestConfig) {
            if (itemId != null && itemId.equalsIgnoreCase(group.getItemId())) {
                return group;
            }
        }

        return null;
    }

    public String getConfigContent() {
        return JsonUtil.toJSONString(mABTestConfigModel);
    }

    public String getIdsJson() {
        if (mABTestConfigModel.abtestConfig == null ||
                mABTestConfigModel.abtestConfig.isEmpty()) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (ABTestItem groupVO : mABTestConfigModel.abtestConfig) {
            ABTestCase testVO = groupVO.getTestCase();
            if (!TextUtils.isEmpty(groupVO.getItemId()) && testVO != null) {
                ids.add(groupVO.getItemId() + "-" + testVO.getCaseId());
            }
        }
        return JsonUtil.toJSONString(ids);
    }
}
