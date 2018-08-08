package com.netease.tools.abtest.uipropprocess;

import com.netease.abtest.uiprop.UIPropCreatorAnno;

import javax.lang.model.element.TypeElement;

/**
 * Created by zyl06 on 2018/8/6.
 */

public class UIPropCreatorModel {
    /**被标注的类的类型信息*/
    public TypeElement typeElement;
    /**被标注的类的对应的注解*/
    public UIPropCreatorAnno anno;

    public UIPropCreatorModel(TypeElement typeElement, UIPropCreatorAnno anno) {
        this.typeElement = typeElement;
        this.anno = anno;
    }
}
