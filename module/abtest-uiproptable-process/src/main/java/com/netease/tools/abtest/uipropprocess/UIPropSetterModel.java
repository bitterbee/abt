package com.netease.tools.abtest.uipropprocess;

import com.netease.abtest.uiprop.UIPropSetterAnno;

import javax.lang.model.element.TypeElement;

/**
 * Created by zyl06 on 2018/8/6.
 */

public class UIPropSetterModel {
    /**被标注的类的类型信息*/
    public TypeElement typeElement;
    /**被标注的类的对应的注解*/
    public UIPropSetterAnno anno;

    public UIPropSetterModel(TypeElement typeElement, UIPropSetterAnno anno) {
        this.typeElement = typeElement;
        this.anno = anno;
    }
}
