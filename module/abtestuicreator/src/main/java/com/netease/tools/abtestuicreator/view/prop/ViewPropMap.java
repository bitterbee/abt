package com.netease.tools.abtestuicreator.view.prop;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ViewPropMap {

    public static List<EditPropView> getEditPropViews(Context context, View v) {
        List<EditPropView> result = new ArrayList<>();
        if (v == null) {
            return result;
        }

        result.add(new BgColorEditPropView(context));

        if (v instanceof TextView) {
            result.add(new TextColorEditPropView(context));
            result.add(new TextStringEditPropView(context));
        }

        // TODO: 2018/7/30 zyl06

        return result;
    }
}
