package com.netease.tools.abtestuicreator.view.prop;

import android.content.Context;
import android.view.View;

import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.RefInvoker;
import com.netease.tools.abtestuicreator.view.prop.concret.ImageSrcPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.TextColorEditPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.TextSizeEditPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.TextStringEditPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.ViewAlphaPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.ViewBgEditPropView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        Map<Class, UIPropCreatorAnno> customCreators = (Map<Class, UIPropCreatorAnno>) RefInvoker.invokeStaticMethod("com.netease.libs.abtest.ABTestUIPropTable",
                "getUIPropCreators", null, null);
        if (customCreators != null) {
            for (Class editPropCls : customCreators.keySet()) {
                if (EditPropView.class.isAssignableFrom(editPropCls)) {
                    addEditPropView(result, context, v, editPropCls);
                }
            }
        }

        return result;
    }

    private static void addEditPropView(List<EditPropView> propViews, Context context, View v, Class<? extends EditPropView> editViewClass) {
        UIPropCreatorAnno anno = editViewClass.getAnnotation(UIPropCreatorAnno.class);
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
