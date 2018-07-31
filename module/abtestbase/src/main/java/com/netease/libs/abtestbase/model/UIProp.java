package com.netease.libs.abtestbase.model;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class UIProp extends BaseModel {
    /**
     * name of the property, ex: background or textSize or textColor
     */
    public String name;

    /**
     * the attr value
     */
    public Object value;

    /**
     * the attr int value
     */
    public int intValue;

    /**
     * the attr float value
     */
    public float floatValue;

    public static String PROP_BG_COLOR = "bgColor";
    public static String PROP_TEXT_STRING = "text";
    public static String PROP_TEXT_COLOR = "textColor";
}
