package com.netease.libs.abtestbase;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;

import com.netease.libs.abtestbase.model.ABTextSizeModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/8/3.
 */

public class ABTestResUtil {

    public static final int NO_RES = 0;

    /**
     *
     * @param ctx
     * @param name 格式 packagename/type/name
     * @return
     */
    public static int getId(Context ctx, String name) {
//        int pkgIndex = res != null ? res.indexOf(".R.") : -1;
//        if (pkgIndex == -1) {
//            return NO_RES;
//        }
//
//        String[] vars = res.substring(pkgIndex + 3, res.length()).split(".");
//        if (vars.length != 2) {
//            return NO_RES;
//        }
//
//        String nameR = res.substring(0, pkgIndex + 2);
//        Object drawable = RefInvoker.getStaticFieldObject(nameR, vars[0]);
//        if (drawable == null) {
//            return NO_RES;
//        }
//
//        Object val = RefInvoker.getFieldObject(drawable, vars[1]);
//        return (val instanceof Integer) ? (int) val : NO_RES;

        if (name == null) {
            return NO_RES;
        }

        String[] parts = name.split("/");
        if (parts.length != 3) {
            return NO_RES;
        }

        Resources res = ctx.getResources();
        return res.getIdentifier(parts[2], parts[1], parts[0]);
    }

    public static String getName(Context context, int res) {
        return context.getResources().getResourceEntryName(res);
    }

    private static final Map<String, Integer> TextSizeUnitMap = new HashMap<String, Integer>() {
        {
            put("px", TypedValue.COMPLEX_UNIT_PX);
            put("dp", TypedValue.COMPLEX_UNIT_DIP);
            put("sp", TypedValue.COMPLEX_UNIT_SP);
            put("pt", TypedValue.COMPLEX_UNIT_PT);
            put("in", TypedValue.COMPLEX_UNIT_IN);
            put("mm", TypedValue.COMPLEX_UNIT_MM);
        }
    };

    public static ABTextSizeModel parseTextSize(String src) {
        if (TextUtils.isEmpty(src) || src.length() < 3) {
            return null;
        }

        String unit = src.substring(src.length() - 2, src.length());
        if (!TextSizeUnitMap.containsKey(unit)) {
            return null;
        }

        String strValue = src.substring(0, src.length() - 2);
        try {
            float value = Float.parseFloat(strValue);

            ABTextSizeModel result = new ABTextSizeModel();
            result.unit = TextSizeUnitMap.get(unit);
            result.size = value;

            return result;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }
}
