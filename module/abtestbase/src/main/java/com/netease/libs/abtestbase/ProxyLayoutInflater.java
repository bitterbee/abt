package com.netease.libs.abtestbase;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;

import java.util.WeakHashMap;

/**
 * Created by zyl06 on 2018/8/4.
 */
public abstract class ProxyLayoutInflater extends LayoutInflater {

    protected LayoutInflater mInflater;
    private static WeakHashMap<LayoutInflater, Boolean> sFactorySets = new WeakHashMap<>();

    public ProxyLayoutInflater(LayoutInflater inflater) {
        super(inflater.getContext());
        mInflater = inflater;
    }

    @Override
    public Context getContext() {
        return mInflater.getContext();
    }

    @Override
    public void setFactory2(Factory2 factory) {
        Boolean isFactorySet = sFactorySets.get(mInflater);
        if (isFactorySet == null || !isFactorySet) {
            sFactorySets.put(mInflater, true);
            mInflater.setFactory2(factory);
        }
    }

    @Override
    public void setFactory(Factory factory) {
        Boolean isFactorySet = sFactorySets.get(mInflater);
        if (isFactorySet == null || !isFactorySet) {
            sFactorySets.put(mInflater, true);
            mInflater.setFactory(factory);
        }
    }

    @Override
    public Filter getFilter() {
        return mInflater.getFilter();
    }

    @Override
    public void setFilter(Filter filter) {
        mInflater.setFilter(filter);
    }


    @Override
    public View inflate(int resource, ViewGroup root) {
        View result = mInflater.inflate(resource, root);

        View created = (root != null && root.getChildCount() > 0) ?
                root.getChildAt(root.getChildCount() - 1) :
                result;

        ViewPathUtil.setXmlLayoutLocalPathTag(getContext(), created, resource);

        onInflate(created);

        return result;
    }

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root) {
        return mInflater.inflate(parser, root);
    }

    @Override
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        /*if (root != null && attachToRoot)
            return inflate(resource, root);*/
        View result = mInflater.inflate(resource, root, attachToRoot);

        View created = (attachToRoot && root != null && root.getChildCount() > 0) ?
                root.getChildAt(root.getChildCount() - 1) :
                result;
        ViewPathUtil.setXmlLayoutLocalPathTag(getContext(), created, resource);

        onInflate(created);

        return result;
    }

    protected abstract void onInflate(View created);

    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return mInflater.inflate(parser, root, attachToRoot);
    }

    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        return (View) RefInvoker.invokeMethod(mInflater, "onCreateView",
                new Class[]{String.class, AttributeSet.class},
                new Object[]{name, attrs});
    }

    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return (View) RefInvoker.invokeMethod(mInflater, "onCreateView",
                new Class[]{View.class, String.class, AttributeSet.class},
                new Object[]{parent, name, attrs});
    }
}