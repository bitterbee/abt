package com.netease.lib.abtest;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.netease.lib.abtest.anno.ABTestInitMethodAnnotation;
import com.netease.lib.abtest.anno.ABTesterAnnotation;
import com.netease.lib.abtest.model.ABTestItem;
import com.netease.lib.abtest.model.ABTestCase;
import com.netease.lib.abtest.util.ABLog;
import com.netease.lib.abtest.util.ObjWeakRef;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zyl06 on 27/07/2017.
 */
public abstract class BaseABTester {
    protected ABTestItem mTestGroup;
    protected String mGroupId;

    private ABTestCase mValidTestVO;
    private Method mInitABMethod;

    public BaseABTester() {
        ABTesterAnnotation anno = getClass().getAnnotation(ABTesterAnnotation.class);
        if (anno != null) {
            mGroupId = anno.groupId();
            mTestGroup = ABTestConfig.getInstance().getTestCase(mGroupId);
            chooseInitMethod(getTestCase());

            ABTestConfig.getInstance().mABTesterRefs.add(new ObjWeakRef<>(this));
        }
    }

    private void chooseInitMethod(ABTestCase testCase) {
        Method[] methods = getClass().getDeclaredMethods();

        Method defaultInit = null;
        for (Method method : methods) {
            ABTestInitMethodAnnotation anno = method.getAnnotation(ABTestInitMethodAnnotation.class);
            if (anno != null) {
                if (testCase != null && TextUtils.equals(anno.testId(), testCase.getTestId())) {
                    mInitABMethod = method;
                    mValidTestVO = testCase;
                    return;
                }

                if (anno.defaultInit()) {
                    defaultInit = method;
                    if (testCase == null) {
                        mInitABMethod = method;
                        return;
                    }
                }
            }
        }

        if (defaultInit != null) {
            mInitABMethod = defaultInit;
        }
    }

    protected void initAB() {
        ABTestCase testVO = getValidTest();
        if (mInitABMethod != null) {
            invokeMethod(mInitABMethod, testVO);
        }
    }

    /**
     * 埋点用这个
     * @return
     */
    public ABTestCase getValidTest() {
        return mValidTestVO;
    }

    protected ABTestCase getTestCase() {
        return mTestGroup != null ? mTestGroup.getTestCase() : null;
    }

    public String getGroupId() {
        return mGroupId;
    }

    /*package*/ void updateConfig(ABTestItem groupVO) {
        mTestGroup = groupVO;
        mInitABMethod = null;
        mValidTestVO = null;
        chooseInitMethod(getTestCase());
        onUpdateConfig();
    }

    // 派生类实现逻辑
    // 业务层需要清理逻辑后，调用 initAB()
    protected abstract void onUpdateConfig();

    private void invokeMethod(Method method, @Nullable ABTestCase testVO) {
        try {
            String groupAccessory = mTestGroup == null ? null : mTestGroup.getAccessory();
            method.invoke(this, groupAccessory, testVO);
        } catch (IllegalAccessException e) {
            ABLog.e(e);
        } catch (IllegalArgumentException e) {
            ABLog.e(e);
        } catch (InvocationTargetException e) {
            ABLog.e(e);
        } catch (Exception e) {
            ABLog.e(e);
        }
    }
}
