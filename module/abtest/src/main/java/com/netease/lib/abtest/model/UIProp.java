package com.netease.lib.abtest.model;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class UIProp extends BaseModel {
    /**
     * name of the attr, ex: background or textSize or textColor
     */
    public String attrName;

    /**
     * the attr value
     */
    public Object attrValue;
}
