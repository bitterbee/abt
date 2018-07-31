package com.netease.tools.abtestuicreator.util;

import android.os.Build;
import android.view.View;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class ClickListenerUtil {

    public static View.OnClickListener getOnClickListener(View view) {
        if (view == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getOnClickListenerV14(view);
        } else {
            return getOnClickListenerV(view);
        }
    }

    public static View.OnLongClickListener getOnLongClickListener(View view) {
        if (view == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getOnLongClickListenerV14(view);
        } else {
            return getOnLongClickListenerV(view);
        }
    }

    //Used for APIs lower than ICS (API 14)
    private static View.OnClickListener getOnClickListenerV(View view) {
        Object result = RefInvoker.getFieldObject(view, "mOnClickListener");
        if (result instanceof View.OnClickListener) {
            return (View.OnClickListener) result;
        }
        return null;
    }

    //Used for new ListenerInfo class structure used beginning with API 14 (ICS)
    private static View.OnClickListener getOnClickListenerV14(View view) {
        Object listenerInfo = RefInvoker.getFieldObject(view, "mListenerInfo");
        if (listenerInfo != null) {
            Object result = RefInvoker.getFieldObject(listenerInfo, "mOnClickListener");
            if (result instanceof View.OnClickListener) {
                return (View.OnClickListener) result;
            }
        }

        return null;
    }


    //Used for APIs lower than ICS (API 14)
    private static View.OnLongClickListener getOnLongClickListenerV(View view) {
        Object result = RefInvoker.getFieldObject(view, "mOnLongClickListener");
        if (result instanceof View.OnLongClickListener) {
            return (View.OnLongClickListener) result;
        }

        return null;
    }

    private static View.OnLongClickListener getOnLongClickListenerV14(View view) {
        Object listenerInfo = RefInvoker.getFieldObject(view, "mListenerInfo");
        if (listenerInfo != null) {
            Object result = RefInvoker.getFieldObject(listenerInfo, "mOnLongClickListener");
            if (result instanceof View.OnLongClickListener) {
                return (View.OnLongClickListener) result;
            }
        }

        return null;
    }
}
