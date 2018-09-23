package com.netease.libs.abtestbase;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by zyl06 on 2018/9/23.
 */

public class ABTestContext {

    private static Application sApplication;

    public static void init(Application app) {
        sApplication = app;
    }

    public static Context getContext() {
        return sApplication;
    }

    // TODO: 2018/9/23 zyl06 这么写感觉不太合适，先放放
    public static String getResEntryName(@IdRes int id) {
        if (id != View.NO_ID) {
            try {
                return sApplication.getResources().getResourceEntryName(id);
            } catch (Resources.NotFoundException e) {
                return null;
            }
        }

        return null;
    }
}
