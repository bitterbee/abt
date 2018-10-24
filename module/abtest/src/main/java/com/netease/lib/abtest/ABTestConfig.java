package com.netease.lib.abtest;

import android.app.Application;
import android.text.TextUtils;

import com.netease.lib.abtest.anno.ABTestUpdateType;
import com.netease.lib.abtest.model.ABTestCase;
import com.netease.lib.abtest.model.ABTestConfigModel;
import com.netease.lib.abtest.model.ABTestItem;
import com.netease.lib.abtest.ui.ABTestActivityLiftcycleCallbackImpl;
import com.netease.lib.abtest.ui.UIPropSetterMgr;
import com.netease.lib.abtest.util.ObjWeakRef;
import com.netease.libs.abtestbase.ABTestContext;
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

    // 后期请求更新
    public void updateABNormalCases(String json) {
        List<ABTestItem> result = JsonUtil.parseArray(json, ABTestItem.class);
        if (result == null) {
            return;
        }
        mABTestConfigModel.abtestLasestNorCases = result;
        ABConfigStore.updateABNormalCases(json);
        notifyAllTesters();
    }

    public void init(Application app) {
        init(app,
                ABConfigStore.readABNormalCases(),
                ABConfigStore.readABUICases());
    }

    public void init(Application app,
                      List<ABTestItem> normalCases,
                      List<ABTestUICase> uiCases) {

        ABTestContext.init(app);

        if (normalCases == null) {
            normalCases = new LinkedList<>();
        }
        if (uiCases == null) {
            uiCases = new LinkedList<>();
        }

        mABTestConfigModel.abtestNorCases = normalCases;
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
            if (tester != null && tester.getItemId() != null && tester.mUpdateType == ABTestUpdateType.IMMEDIATE_UPDATE) {
                ABTestItem group = getLatestNormalCase(tester.getItemId());
                if (group != null) {
                    tester.updateConfig(group);
                }
            }
        }
    }

    public ABTestItem getLatestNormalCase(String itemId) {
        if (mABTestConfigModel.abtestLasestNorCases != null) {
            for (ABTestItem group : mABTestConfigModel.abtestLasestNorCases) {
                if (itemId != null && itemId.equalsIgnoreCase(group.getItemId())) {
                    return group;
                }
            }
        }
        return null;
    }

    public ABTestItem getNormalCase(String itemId, ABTestUpdateType updateType) {
        if (itemId == null) {
            return null;
        }

        if (updateType != ABTestUpdateType.COLD_UPDATE) {
            ABTestItem group = getLatestNormalCase(itemId);
            if (group != null) {
                return group;
            }
        }

        if (mABTestConfigModel.abtestNorCases != null) {
            for (ABTestItem group : mABTestConfigModel.abtestNorCases) {
                if (itemId.equalsIgnoreCase(group.getItemId())) {
                    return group;
                }
            }
        }

        return null;
    }

    public String getConfigContent() {
        return JsonUtil.toJSONString(mABTestConfigModel);
    }

    public String getIdsJson() {
        if (mABTestConfigModel.abtestNorCases == null ||
                mABTestConfigModel.abtestNorCases.isEmpty()) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (ABTestItem groupVO : mABTestConfigModel.abtestNorCases) {
            ABTestCase testVO = groupVO.getTestCase();
            if (!TextUtils.isEmpty(groupVO.getItemId()) && testVO != null) {
                ids.add(groupVO.getItemId() + "-" + testVO.getCaseId());
            }
        }
        return JsonUtil.toJSONString(ids);
    }
}
