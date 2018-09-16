package com.netease.lib.abtest;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.netease.lib.abtest.anno.ABTestInitMethodAnnotation;
import com.netease.lib.abtest.anno.ABTesterAnno;
import com.netease.lib.abtest.model.ABTestCase;
import com.netease.lib.abtest.model.ABTestItem;
import com.netease.lib.abtest.util.ObjWeakRef;
import com.netease.libs.abtestbase.ABLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zyl06 on 27/07/2017.
 */
public abstract class BaseABTester {
    protected ABTestItem mTestCase;
    protected String mItemId;

    private ABTestCase mValidTestVO;
    private Method mInitABMethod;

    private boolean mIsInited = false;

    public BaseABTester() {
        ABTesterAnno anno = getClass().getAnnotation(ABTesterAnno.class);
        if (anno != null) {
            mItemId = anno.itemId();
            mTestCase = ABTestConfig.getInstance().getTestCase(mItemId);
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
                if (testCase != null && TextUtils.equals(anno.caseId(), testCase.getCaseId())) {
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
        if (!mIsInited) {
            mIsInited = true;

            ABTestCase testVO = getValidTest();
            if (mInitABMethod != null) {
                invokeMethod(mInitABMethod, testVO);
            }
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
        return mTestCase != null ? mTestCase.getTestCase() : null;
    }

    public String getItemId() {
        return mItemId;
    }

    /*package*/ void updateConfig(ABTestItem groupVO) {
        mTestCase = groupVO;
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
            String groupAccessory = mTestCase == null ? null : mTestCase.getAccessory();
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
