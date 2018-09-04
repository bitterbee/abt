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
     * @param v
     */
    public static void markIgnore(View v) {
        if (v != null) {
            v.setTag(R.string.abtest_ignore_tag, new Object());
        }
    }

    public static String getIdName(View v) {
        if (v.getId() == View.NO_ID) {
            return "NO_ID";
        }

        try {
            Context context = v.getContext();
            return context.getResources().getResourceEntryName(v.getId());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return "WRONG_ID";
        }
    }
}
