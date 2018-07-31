package com.netease.tools.abtestuicreator.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.netease.tools.abtestuicreator.R;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ViewUtil {

    /**
     * 用来忽略一些View
     *
     * @param view
     */
    public static void markIgnore(View view) {
        if (view == null)
            return;
        view.setTag(R.string.abtest_ignore_tag, new Object());
    }

    public static String getIdName(View view) {
        Context context = view.getContext();
        String result = null;
        try {
            result = context.getResources().getResourceEntryName(view.getId());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
