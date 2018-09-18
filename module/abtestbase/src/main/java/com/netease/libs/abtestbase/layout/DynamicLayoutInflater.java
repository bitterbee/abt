package com.netease.libs.abtestbase.layout;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.CryptoUtil;
import com.netease.libs.abtestbase.R;
import com.netease.libs.abtestbase.RefInvoker;

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

    private Class mIdClass;

    public DynamicLayoutInflater(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        String idName = context.getPackageName() + ".R$id";

        try {
            Class<?> cls = Class.forName("android.content.res.XmlBlock");
            mXmlBlockConstruct = cls.getDeclaredConstructor(byte[].class);
            mXmlBlockConstruct.setAccessible(true);

            mParserMethod = cls.getDeclaredMethod("newParser");
            mParserMethod.setAccessible(true);

            mIdClass = Class.forName(idName);

            mIsValid = true;
        } catch(RuntimeException e) {
            ABLog.e(e);
        } catch (ClassNotFoundException e) {
            ABLog.e(e);
        } catch(Exception e) {
            ABLog.e(e);
        }
    }

    public boolean isValid() {
        return mIsValid;
    }

    public View inflate(String content, @Nullable ViewGroup root, boolean attachToRoot) {

        if (!isValid()) {
            return null;
        }

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

        View res = null;
        try {
            res = mLayoutInflater.inflate(xmlParser, root, attachToRoot);
            res.setTag(R.string.abtest_tag_replace_xml_dylayout, content);

            applyViewIds(res);
        } catch (Exception e) {
            ABLog.e(e);
        }

        return res;
    }

    private void applyViewIds(View v) {
        Object objTag = v.getTag();
        if (objTag instanceof String) {
            String tag = (String) objTag;
            if (tag.startsWith("R.id.")) {
                String strId = tag.substring(5, tag.length());

                Object id = RefInvoker.getFieldObject(null, mIdClass, strId, true);
                if (id instanceof Integer) {
                    v.setId((int) id);
                }
            }
        }

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                applyViewIds(vg.getChildAt(i));
            }
        }
    }
}