package com.netease.tools.abtestuicreator.notify;

import android.util.SparseArray;

import com.netease.lib.abtest.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/31.
 */

public class NotifyMgr {

    private SparseArray<List<ISubscriber>> mEventReceivers = new SparseArray<>();

    private static NotifyMgr sInstance = null;
    public static NotifyMgr getInstance() {
        if (sInstance == null) {
            synchronized (NotifyMgr.class) {
                if (sInstance == null) {
                    sInstance = new NotifyMgr();
                }
            }
        }
        return sInstance;
    }

    private NotifyMgr() {
    }

    public boolean register(ISubscriber receiver, int eventType) {
        List<ISubscriber> receivers = mEventReceivers.get(eventType);
        if (receivers == null) {
            receivers = new ArrayList<>();
            mEventReceivers.put(eventType, receivers);
        }

        if (!receivers.contains(receiver)) {
            receivers.add(receiver);
            return true;
        }

        return false;
    }

    public boolean unregister(ISubscriber receiver) {
        boolean success = false;

        int count = mEventReceivers.size();
        for (int i=0; i<count; i++) {
             int key = mEventReceivers.keyAt(i);
            List<ISubscriber> receivers = mEventReceivers.get(key);
            success |= receivers.remove(receiver);
        }

        return success;
    }

    public void notify(int eventType) {
        notify(eventType, null);
    }

    public void notify(int eventType, Object event) {
        List<ISubscriber> receivers = mEventReceivers.get(eventType);
        if (CollectionUtil.isEmpty(receivers)) {
            return;
        }

        for (ISubscriber receiver : receivers) {
            receiver.onReceiveEvent(eventType, event);
        }
    }
}
