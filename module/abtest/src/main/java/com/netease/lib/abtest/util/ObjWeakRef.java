package com.netease.lib.abtest.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by zyl06 on 10/08/2017.
 */
public class ObjWeakRef<T> extends WeakReference<T> {
    private int mHashCode;

    public ObjWeakRef(T r) {
        super(r);
        mHashCode = r != null ? r.hashCode() : 0;
    }

    public ObjWeakRef(T r, ReferenceQueue<? super T> q) {
        super(r, q);
        mHashCode = r != null ? r.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ObjWeakRef) {
            ObjWeakRef ref = (ObjWeakRef) o;
            return ref.get() != null && ref.get() == this.get();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mHashCode;
    }
}