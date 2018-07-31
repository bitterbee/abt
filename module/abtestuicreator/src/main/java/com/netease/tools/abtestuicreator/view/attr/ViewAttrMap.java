package com.netease.tools.abtestuicreator.view.attr;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ViewAttrMap {

    public static List<EditAttrView> getEditAttrViews(Context context, View v) {
        List<EditAttrView> result = new ArrayList<>();
        if (v == null) {
            return result;
        }

        result.add(new BgColorEditAttrView(context));

        if (v instanceof TextView) {
            result.add(new TextColorEditAttrView(context));
            result.add(new TextStringEditAttrView(context));
        }

        // TODO: 2018/7/30 zyl06

        return result;
    }
}
