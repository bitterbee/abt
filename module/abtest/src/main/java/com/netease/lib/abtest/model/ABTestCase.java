package com.netease.lib.abtest.model;

/**
 * Created by zyl06 on 27/07/2017.
 */
public class ABTestCase extends BaseModel {

    /** 测试ID */
    private String testId;
    /** 扩展字段 */
    private String accessory;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }
}
