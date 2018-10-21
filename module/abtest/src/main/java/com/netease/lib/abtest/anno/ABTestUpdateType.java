package com.netease.lib.abtest.anno;

/**
 * Created by zyl06 on 2018/10/21.
 */

public enum ABTestUpdateType {
    IMMEDIATE_UPDATE(0),
    HOT_UPDATE(1),
    COLD_UPDATE(2);

    private int mType = 0;
    ABTestUpdateType(int type) {
        this.mType = type;
    }
}
