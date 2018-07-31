package com.netease.tools.abtestuicreator.notify;

/**
 * Created by zyl06 on 2018/7/31.
 */

public interface ISubscriber {
    void onReceiveEvent(int type, Object event);
}
