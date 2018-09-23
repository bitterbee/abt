package com.netease.libs.abtestbase.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class ABTestUICase extends BaseModel {
    private String viewPath;
    private List<UIProp> uiProps = new LinkedList<>();

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public List<UIProp> getUiProps() {
        return uiProps;
    }

    public void setUiProps(List<UIProp> uiProps) {
        this.uiProps = uiProps;
    }
}
