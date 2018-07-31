package com.netease.lib.abtest.util;

import java.util.Collection;
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
}
