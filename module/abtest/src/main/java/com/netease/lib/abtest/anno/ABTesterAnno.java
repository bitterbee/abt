package com.netease.lib.abtest.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zyl06 on 27/07/2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ABTesterAnno {
    String itemId();
    ABTestUpdateType updateType() default ABTestUpdateType.IMMEDIATE_UPDATE;
}
