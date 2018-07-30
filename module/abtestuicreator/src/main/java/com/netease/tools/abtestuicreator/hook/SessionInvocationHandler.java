package com.netease.tools.abtestuicreator.hook;

import android.view.View;

import com.netease.tools.abtestuicreator.util.LogUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class SessionInvocationHandler implements InvocationHandler {

    private Object mSession;
    private WindowSessionCallback mCallback;

    public SessionInvocationHandler(Object session, WindowSessionCallback callback) {
        mSession = session;
        mCallback = callback;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("addToDisplay")) {
            mCallback.onAddToDisplay(getDecorView(args[0]));
        }
        if (method.getName().equals("add")) {
            mCallback.onAdd(getDecorView(args[0]));
        }
        if (method.getName().equals("addWithoutInputChannel")) {
            mCallback.onAddWithoutInputChannel(getDecorView(args[0]));
        }
        if (method.getName().equals("addToDisplayWithoutInputChannel")) {
            mCallback.onAddToDisplayWithoutInputChannel(getDecorView(args[0]));
        }
        return method.invoke(mSession, args);
    }

    private View getDecorView(Object w) {
        try {
            Class wClazz = Class.forName("android.view.ViewRootImpl$W");
            Field ancestorField = wClazz.getDeclaredField("mViewAncestor");
            ancestorField.setAccessible(true);
            WeakReference ancestor = (WeakReference) ancestorField.get(w);
            Object viewRootImpl = ancestor.get();
            if (viewRootImpl == null)
                return null;
            Class viewRootImplClazz = Class.forName("android.view.ViewRootImpl");
            Field viewField = viewRootImplClazz.getDeclaredField("mView");
            viewField.setAccessible(true);
            return (View) viewField.get(viewRootImpl);
        } catch (ClassNotFoundException e) {
            LogUtil.d("ClassNotFound " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            LogUtil.d("NoSuchField " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LogUtil.d("IllegalAccess " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}