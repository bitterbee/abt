package com.netease.lib.abtest.model;

/**
 * Created by zyl06 on 27/07/2017.
 */
public class ABTestItem extends BaseModel {
    /** 组别ID */
    private String itemId;

    /** 扩展字段 */
    private String accessory;

    /** A/B测试 */
    private ABTestCase testCase;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

    public ABTestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(ABTestCase testCase) {
        this.testCase = testCase;
    }
}
