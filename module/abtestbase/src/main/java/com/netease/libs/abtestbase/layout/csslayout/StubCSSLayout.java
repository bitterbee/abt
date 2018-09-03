package com.netease.libs.abtestbase.layout.csslayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.csslayout.CSSLayoutContext;
import com.facebook.csslayout.CSSNode;
import com.facebook.csslayout.Spacing;
import com.netease.libs.abtestbase.R;

/**
 * Created by zyl06 on 2018/8/31.
 */
public class StubCSSLayout extends FrameLayout {

    private CSSLayoutContext mLayoutContext;

    public StubCSSLayout(Context context) {
        super(context, null);
    }

    public StubCSSLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StubCSSLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StubCSSLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        int color = getResources().getColor(R.color.transparent);
        setBackgroundColor(color);
    }

    public void bindContainer(ViewGroup bindView, CSSNode node) {
        CSSLayoutUtil.bindNode(this, node);

        ViewGroup.LayoutParams bindViewLP = bindView.getLayoutParams();
        if (bindViewLP != null) {
            setLayoutParams(bindViewLP);
        }

        bindView.setPadding(0, 0, 0, 0);
        bindView.addView(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setClipChildren(bindView.getClipChildren());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildNode(widthMeasureSpec, heightMeasureSpec);
        getCSSNode().calculateLayout(getLayoutContext());
        assignNodeLayoutParams();
    }

    private CSSNode getCSSNode() {
        return CSSLayoutUtil.getNode(this);
    }

    private void measureChildNode(int widthMeasureSpec, int heightMeasureSpec) {
        int childNodeViewsCount = getChildCount();
        for (int i = 0; i < childNodeViewsCount; i++) {
            View view = getChildAt(i);
            CSSNode node = CSSLayoutUtil.getNode(view);

            if (node.getSizeToFit()) {
                int margins = (int) (node.getMargin().get(Spacing.LEFT) + node.getMargin().get(Spacing.RIGHT));
                measureChild(view, widthMeasureSpec - margins, heightMeasureSpec);

                node.setNoDirtyStyleWidth(view.getMeasuredWidth());
                node.setNoDirtyStyleHeight(view.getMeasuredHeight());
            }

            if (view instanceof StubCSSLayout) {
                StubCSSLayout vg = (StubCSSLayout) view;
                vg.measureChildNode(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    private void assignNodeLayoutParams() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            CSSNode node = CSSLayoutUtil.getNode(view);

            if (view != null && node != null) {
                int x = (int) node.getLayoutX();
                int y = (int) node.getLayoutY();
                int width = (int) node.getLayoutWidth();
                int height = (int) node.getLayoutHeight();

                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
                if (lp == null) {
                    lp = new MarginLayoutParams(width, height);
                } else {
                    lp.width = width;
                    lp.height = height;
                }

                lp.setMargins(x, y, 0, 0);
                view.setLayoutParams(lp);

                if (view instanceof StubCSSLayout) {
                    StubCSSLayout viewGroup = (StubCSSLayout) view;
                    viewGroup.assignNodeLayoutParams();
                }
            }
        }
    }

    private CSSLayoutContext getLayoutContext() {
        if (mLayoutContext == null) {
            mLayoutContext = new CSSLayoutContext();
        }

        return mLayoutContext;
    }
}
