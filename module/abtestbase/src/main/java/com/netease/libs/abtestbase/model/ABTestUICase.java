package com.netease.libs.abtestbase.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class ABTestUICase extends BaseModel {
    private String viewId;
    private List<UIProp> uiProps = new LinkedList<>();

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public List<UIProp> getUiProps() {
        return uiProps;
    }

    public void setUiProps(List<UIProp> uiProps) {
        this.uiProps = uiProps;
    }
}
