package com.netease.tools.abtestuicreator.view.prop;

import android.content.Context;
import android.view.View;

import com.netease.tools.abtestuicreator.view.prop.concret.ImageSrcPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.TextColorEditPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.TextSizeEditPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.TextStringEditPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.ViewAlphaPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.ViewBgEditPropView;

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

        addEditPropView(result, context, v, ViewBgEditPropView.class);
        addEditPropView(result, context, v, ViewAlphaPropView.class);
        addEditPropView(result, context, v, TextColorEditPropView.class);
        addEditPropView(result, context, v, TextStringEditPropView.class);
        addEditPropView(result, context, v, TextSizeEditPropView.class);
        addEditPropView(result, context, v, ImageSrcPropView.class);

        return result;
    }

    private static void addEditPropView(List<EditPropView> propViews, Context context, View v, Class<? extends EditPropView> editViewClass) {
        ViewPropAnno anno = editViewClass.getAnnotation(ViewPropAnno.class);
        if (anno != null && anno.viewType().isAssignableFrom(v.getClass())) {
            try {
                EditPropView propView = editViewClass.getConstructor(Context.class).newInstance(context);
                propViews.add(propView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
