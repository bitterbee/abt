package com.netease.demo.abtest.second;

import android.text.TextUtils;

/**
 * Created by zyl06 on 2018/9/16.
 */
public enum TabType {

    Home("首页"),
    Discovery("发现"),
    ShoppingCart("购物车"),
    UserPage("个人");

    String value;
    TabType(String v) {
        value = v;
    }

    @Override
    public String toString() {
        return value;
    }

    public static int getTabPosition(String tabName) {
        if (TextUtils.equals(Home.toString(), tabName)) {
            return 0;
        } else if (TextUtils.equals(Discovery.toString(), tabName)) {
            return 1;
        } else if (TextUtils.equals(ShoppingCart.toString(), tabName)) {
            return 2;
        } else if (TextUtils.equals(UserPage.toString(), tabName)) {
            return 3;
        }

        return -1;
    }
}
