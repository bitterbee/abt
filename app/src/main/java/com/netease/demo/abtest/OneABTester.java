package com.netease.demo.abtest;

import android.support.annotation.Nullable;

import com.netease.lib.abtest.BaseABTester;
import com.netease.lib.abtest.anno.ABTestInitMethodAnnotation;
import com.netease.lib.abtest.anno.ABTesterAnno;
import com.netease.lib.abtest.model.ABTestCase;

/**
 * Created by zyl06 on 2018/7/26.
 */
@ABTesterAnno(groupId = "SimpleTest_001")
public class OneABTester extends BaseABTester {

    private String name;

    public OneABTester() {
    }

    @Override
    protected void onUpdateConfig() {

    }

    @ABTestInitMethodAnnotation(testId = "000", defaultInit = true)
    public void initA(@Nullable String accessory, @Nullable ABTestCase testVO) {
        name = "hanmeimei";
    }

    /**
     *
     * @param accessory
     * @param testVO
     */
    @ABTestInitMethodAnnotation(testId = "001")
    public void initB(@Nullable String accessory, @Nullable ABTestCase testVO) {
        name = "lilei";
    }

    public String getName() {
        return name;
    }
}
