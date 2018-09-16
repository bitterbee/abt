package com.netease.lib.abtest.model;

import com.netease.libs.abtestbase.model.BaseModel;

/**
 * Created by zyl06 on 27/07/2017.
 */
public class ABTestCase extends BaseModel {

    /** 测试ID */
    private String caseId;
    /** 扩展字段 */
    private String accessory;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }
}
