package com.netease.lib.abtest.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 22/01/2018.
 * AbTest配置信息
 */
public class ABTestConfigModel extends BaseModel {
    // 业务逻辑 abtest 测试配置
    public List<ABTestItem> abtestConfig = new LinkedList<>();
    // UI 逻辑 abtest 测试配置
    public List<ABTestUICase> abtestUICases = new LinkedList<>();
}
