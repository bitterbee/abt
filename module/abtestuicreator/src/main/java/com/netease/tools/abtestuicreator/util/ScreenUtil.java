package com.netease.tools.abtestuicreator.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ScreenUtil {

    private static int sStatusBarHeight = 0;

    public static int getStatusBarHeight(Context context) {
        if (sStatusBarHeight > 0) {
            return sStatusBarHeight;
        }

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sStatusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sStatusBarHeight;
    }


}
