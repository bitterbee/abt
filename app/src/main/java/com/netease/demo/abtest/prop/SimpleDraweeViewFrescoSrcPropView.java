package com.netease.demo.abtest.prop;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.netease.abtest.uiprop.UIPropCreatorAnno;
import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.RefInvoker;
import com.netease.tools.abtestuicreator.view.prop.EditPropView;

/**
 * Created by zyl06 on 2018/9/25.
 */
@UIPropCreatorAnno(viewType = SimpleDraweeView.class, name = "fresco_src")
public class SimpleDraweeViewFrescoSrcPropView  extends EditPropView<String> {

    private Uri mOldValue;

    public SimpleDraweeViewFrescoSrcPropView(Context context) {
        this(context, null);
    }

    public SimpleDraweeViewFrescoSrcPropView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleDraweeViewFrescoSrcPropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleDraweeViewFrescoSrcPropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onRestoreValue(View v) {
        super.onRestoreValue(v);
        if (mOldValue != null) {
            ((SimpleDraweeView) v).setImageURI(mOldValue);
        }
    }

    @Override
    protected void onUpdateView(View v, Editable value) {
        super.onUpdateView(v, value);

        try {
            mNewValue = value.toString();
            Uri uri = Uri.parse(mNewValue);
            ((SimpleDraweeView) v).setImageURI(uri);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBindView(View v) {
        try {
            PipelineDraweeController controller = (PipelineDraweeController) ((SimpleDraweeView) v).getController();
            if (controller != null) {
                Object dataSourceSupplier =
                        RefInvoker.invokeMethod(controller, "getDataSourceSupplier", null, null);
                AbstractDraweeControllerBuilder builder = (AbstractDraweeControllerBuilder) RefInvoker.getFieldObject(dataSourceSupplier, "this$0");
                ImageRequest imageRequest = (ImageRequest) builder.getImageRequest();

                if (imageRequest != null) {
                    mOldValue = imageRequest.getSourceUri();
                }

                if (mOldValue != null) {
                    setValue(mOldValue.toString());
                }
            }
        } catch (Exception e) {
            ABLog.e(e);
        }
    }
}
