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
import com.netease.tools.abtestuicreator.view.prop.concret.ViewGroupCssLayoutPropView;
import com.netease.tools.abtestuicreator.view.prop.concret.ViewReplacePropView;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/30.
 */
public class ViewPropMap {

    public static Collection<EditPropView> getEditPropViews(Context context, View v) {
        Map<String, EditPropView> result = new LinkedHashMap<>();
        if (v == null) {
            return result.values();
        }

        addEditPropView(result, context, v, ViewBgEditPropView.class);
        addEditPropView(result, context, v, ViewAlphaPropView.class);
        addEditPropView(result, context, v, TextColorEditPropView.class);
        addEditPropView(result, context, v, TextStringEditPropView.class);
        addEditPropView(result, context, v, TextSizeEditPropView.class);
        addEditPropView(result, context, v, ImageSrcPropView.class);
        addEditPropView(result, context, v, ViewReplacePropView.class);
        addEditPropView(result, context, v, ViewGroupCssLayoutPropView.class);

        Map<Class, UIPropCreatorAnno> customCreators = (Map<Class, UIPropCreatorAnno>) RefInvoker.invokeStaticMethod("com.netease.libs.abtest.ABTestUIPropTable",
                "getUIPropCreators", null, null);
        if (customCreators != null) {
            for (Class editPropCls : customCreators.keySet()) {
                if (EditPropView.class.isAssignableFrom(editPropCls)) {
                    addEditPropView(result, context, v, editPropCls);
                }
            }
        }

        return result.values();
    }

    private static void addEditPropView(Map<String, EditPropView> propViews, Context context, View v, Class<? extends EditPropView> editViewClass) {
        UIPropCreatorAnno anno = editViewClass.getAnnotation(UIPropCreatorAnno.class);
        if (anno != null && anno.viewType().isAssignableFrom(v.getClass())) {
            try {
                EditPropView propView = editViewClass.getConstructor(Context.class).newInstance(context);
                propViews.put(anno.name(), propView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
