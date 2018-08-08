package com.netease.lib.abtest.ui.layout;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.CryptoUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by zyl06 on 2018/8/8.
 */

public class DynamicLayoutInflater {

    private LayoutInflater mLayoutInflater;
    private Constructor<?> mXmlBlockConstruct;
    private Method mParserMethod;
    private boolean mIsValid = false;

    public DynamicLayoutInflater(Context context) {
        mLayoutInflater = LayoutInflater.from(context);

        try {
            Class<?> cls = Class.forName("android.content.res.XmlBlock");
            mXmlBlockConstruct = cls.getDeclaredConstructor(byte[].class);
            mXmlBlockConstruct.setAccessible(true);

            mParserMethod = cls.getDeclaredMethod("newParser");
            mParserMethod.setAccessible(true);

            mIsValid = true;
        } catch(RuntimeException e) {
            ABLog.e(e);
        } catch(Exception e) {
            ABLog.e(e);
        }
    }

    public boolean isValid() {
        return mIsValid;
    }

    public View inflate(String content, @Nullable ViewGroup root, boolean attachToRoot) {

        XmlResourceParser xmlParser = null;
        try {
            byte[] xml = CryptoUtil.base64Decode(content);
            Object xmlBlock = mXmlBlockConstruct.newInstance(new Object[]{xml});
            xmlParser = (XmlResourceParser) mParserMethod.invoke(xmlBlock, null);
        } catch (Exception e) {
            ABLog.e(e);
        }

        if (xmlParser == null) {
            return null;
        }

        return mLayoutInflater.inflate(xmlParser, root, attachToRoot);
    }
}
