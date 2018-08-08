package com.netease.tools.abtest.uipropprocess;

import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Messager;

/**
 * Created by zyl06 on 2018/8/6.
 */

public abstract class BaseClassGenerator {
    protected Messager messager;

    public BaseClassGenerator(Messager messager) {
        this.messager = messager;
    }

    public abstract String className();
    public abstract TypeSpec generate(String packageName);
    public abstract void printError(Exception e);
}
