package com.netease.lib.abtest.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zyl06 on 2018/7/29.
 */

public class CollectionUtil {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isEmpty(Map collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> T firstItem(Collection<T> collection) {
        if (collection == null) return null;
        for (T item : collection) {
            return item;
        }
        return null;
    }

    public static <T> T firstItem(T[] collection) {
        if (collection == null) return null;
        for (T item : collection) {
            return item;
        }
        return null;
    }

    public static <T> T lastItem(List<T> collection) {
        if (collection == null || collection.isEmpty()) return null;

        return collection.get(collection.size() - 1);
    }
}
