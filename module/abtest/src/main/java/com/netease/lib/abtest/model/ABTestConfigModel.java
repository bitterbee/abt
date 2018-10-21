package com.netease.lib.abtest.model;

import com.netease.libs.abtestbase.model.ABTestUICase;
import com.netease.libs.abtestbase.model.BaseModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 22/01/2018.
 * AbTest配置信息
 */
public class ABTestConfigModel extends BaseModel {
    // 业务逻辑 abtest 测试配置
    public List<ABTestItem> abtestNorCases = new LinkedList<>();

    // 最新的业务逻辑 abtest 测试配置，程序后期请求更新得到的 abtest 数据
    public List<ABTestItem> abtestLasestNorCases = new LinkedList<>();

    // UI 逻辑 abtest 测试配置
    public List<ABTestUICase> abtestUICases = new LinkedList<>();
}
