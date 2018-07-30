package com.netease.tools.abtestuicreator;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupWindow;

import com.netease.tools.abtestuicreator.hook.ProxyInstrumentation;
import com.netease.tools.abtestuicreator.hook.SessionInvocationHandler;
import com.netease.tools.abtestuicreator.hook.WindowSessionCallback;
import com.netease.tools.abtestuicreator.touch.IReplaceLongClickListenerFactory;
import com.netease.tools.abtestuicreator.touch.UIEditReplaceLongClickListenerFactory;
import com.netease.tools.abtestuicreator.util.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class Monitor implements WindowSessionCallback {

    private static Monitor mInstance;
    private Class mPhoneWindowDecorClazz;
    private Class mPopWindowDecorClazz;
    private IReplaceLongClickListenerFactory mReplaceLongClickListenerFactory;

    private Monitor() {
    }

    public void init(Context context) {
        init(context, new UIEditReplaceLongClickListenerFactory());
    }

    public void init(Context context,
                     IReplaceLongClickListenerFactory replaceLongClickListenerFactory) {
        this.mReplaceLongClickListenerFactory = replaceLongClickListenerFactory;
        hookWindowManager();
        hookInstrumentation();
    }

    private void hookWindowManager() {
        try {
            Class iWindowSessionClazz = Class.forName("android.view.IWindowSession");
            Class<?> wmGlobalClazz = Class.forName("android.view.WindowManagerGlobal");
            Method getInstanceMethod = wmGlobalClazz.getDeclaredMethod("getInstance");
            getInstanceMethod.setAccessible(true);
            //获取WindowManagerGlobal实例
            Object windowManagerGlobal = getInstanceMethod.invoke(null);
            Method getSessionMethod = Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                    wmGlobalClazz.getDeclaredMethod("getWindowSession") :
                    wmGlobalClazz.getDeclaredMethod("getWindowSession", Looper.class);
            getSessionMethod.setAccessible(true);
            Object sessionObj = Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                    getSessionMethod.invoke(windowManagerGlobal) :
                    getSessionMethod.invoke(windowManagerGlobal, Looper.getMainLooper());

            Field sessionField = wmGlobalClazz.getDeclaredField("sWindowSession");
            sessionField.setAccessible(true);
            Object sessionProxy = Proxy.newProxyInstance(iWindowSessionClazz.getClassLoader(),
                    new Class[]{iWindowSessionClazz}, new SessionInvocationHandler(sessionObj, this));
            sessionField.set(windowManagerGlobal, sessionProxy);
        } catch (ClassNotFoundException e) {
            LogUtil.d("ClassNotFound " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            LogUtil.d("NoSuchMethod " + e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            LogUtil.d("InvocationTarget " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LogUtil.d("IllegalAccess " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            LogUtil.d("NoSuchField " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void hookInstrumentation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            hookInstrumentationGTSDK18();
        } else {
            hookInstrumentationLTSDK17();
        }
    }

    /**
     * api >= 18
     */
    private void hookInstrumentationGTSDK18() {
        try {
            Class activityThreadClazz = Class.forName("android.app.ActivityThread");
            Field activityThreadField = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            Object activityThread = activityThreadField.get(null);
            Field instrumentationField = activityThreadClazz.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            instrumentationField.set(activityThread, new ProxyInstrumentation());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * api <=17
     */
    private void hookInstrumentationLTSDK17() {
        try {
            Class activityThreadClazz = Class.forName("android.app.ActivityThread");
            Method currentThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentThreadMethod.setAccessible(true);
            Object activityThread = currentThreadMethod.invoke(null);
            Field instrumentationField = activityThreadClazz.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            instrumentationField.set(activityThread, new ProxyInstrumentation());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String getDecorViewClassPath() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return "com.android.internal.policy.PhoneWindow$DecorView";
        } else {
            return "com.android.internal.policy.impl.PhoneWindow$DecorView";
        }
    }

    private Window getPhoneWindow(View decorView) {
        try {
            if (mPhoneWindowDecorClazz == null) {
                mPhoneWindowDecorClazz = Class.forName(getDecorViewClassPath());
            }
            //获取外部类的引用
            Field outerField = mPhoneWindowDecorClazz.getDeclaredField("this$0");
            outerField.setAccessible(true);
            Object window = outerField.get(decorView);
            if (window instanceof Window) {
                return (Window) window;
            }
        } catch (ClassNotFoundException e) {
            LogUtil.e("ClassNotFound " + e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtil.e("IllegalAccess " + e.getMessage());
        } catch (NoSuchFieldException e) {
            LogUtil.e("NoSuchField " + e.getMessage());
        }
        return null;
    }

    private PopupWindow getPopupWindow(View decorView) {

        try {
            if (mPopWindowDecorClazz == null) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
                    mPopWindowDecorClazz = Class.forName("android.widget.PopupWindow$PopupDecorView");
                else
                    mPopWindowDecorClazz = Class.forName("android.widget.PopupWindow$PopupViewContainer");
            }
            //获取外部类的引用
            Field outerField = mPopWindowDecorClazz.getDeclaredField("this$0");
            outerField.setAccessible(true);
            Object window = outerField.get(decorView);
            if (window instanceof PopupWindow) {
                return (PopupWindow) window;
            }
        } catch (ClassNotFoundException e) {
            LogUtil.e("ClassNotFound " + e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtil.e("IllegalAccess " + e.getMessage());
        } catch (NoSuchFieldException e) {
            LogUtil.e("NoSuchField " + e.getMessage());
        }

        return null;
    }


    public static Monitor getInstance() {
        if (mInstance == null) {
            synchronized (Monitor.class) {
                if (mInstance == null)
                    mInstance = new Monitor();
            }
        }
        return mInstance;
    }

    private void setWindowCallback(Window window, View decorView) {
        View.OnLongClickListener replaceLongClickListener = mReplaceLongClickListenerFactory != null ?
                mReplaceLongClickListenerFactory.build(decorView) :
                null;
        window.setCallback((Window.Callback) Proxy.newProxyInstance(window.getClass().getClassLoader(),
                new Class[]{Window.Callback.class},
                new ActivityCallbackHandler(window.getCallback(), new OnTouchCallback(decorView, null, replaceLongClickListener))));
    }

    /**
     * 拦截PopupWindow的点击事件，由于PopupWindow不是基于Window的，所以不使用Callback
     */
    private void setPopupWindowCallback(PopupWindow window, View view) {
        try {
            Field touchField = window.getClass().getDeclaredField("mTouchInterceptor");
            touchField.setAccessible(true);
            View.OnTouchListener listener = (View.OnTouchListener) touchField.get(window);

            View.OnLongClickListener replaceLongClickListener = null;
            if (mReplaceLongClickListenerFactory != null) {
                replaceLongClickListener = mReplaceLongClickListenerFactory.build(view);
            }
            window.setTouchInterceptor(new OnTouchCallback(view, listener, replaceLongClickListener));
        } catch (NoSuchFieldException e) {
            LogUtil.e("NoSuchField " + e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtil.e("IllegalAccess " + e.getMessage());
        }
    }

    @Override
    public void onAddToDisplay(View decorView) {
        if (decorView == null || isIgnoreActivityView(decorView)) {
            return;
        }

        if (decorView.getClass().getSimpleName().equals("DecorView")) {
            Window window = getPhoneWindow(decorView);
            setWindowCallback(window, decorView);
        } else if (decorView.getClass().getSimpleName().equals("PopupDecorView") ||
                decorView.getClass().getSimpleName().equals("PopupViewContainer")) {
            if (ViewUtil.isIgnoredView(((ViewGroup) decorView).getChildAt(0)))
                return;
            PopupWindow window = getPopupWindow(decorView);
            setPopupWindowCallback(window, decorView);
        }
    }

    private boolean isIgnoreActivityView(View decorView) {
        if (decorView != null && decorView.getContext() instanceof Activity) {
            Activity activity = (Activity) decorView.getContext();
            IgnoreHookActivityAnnotation anno = activity.getClass().getAnnotation(IgnoreHookActivityAnnotation.class);
            return anno != null;
        }
        return false;
    }

    @Override
    public void onAdd(View decorView) {

    }

    @Override
    public void onAddWithoutInputChannel(View decorView) {

    }

    @Override
    public void onAddToDisplayWithoutInputChannel(View decorView) {

    }
}