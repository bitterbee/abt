package com.netease.tools.abtestuicreator.util;

import android.view.View;
import android.view.ViewGroup;

import com.netease.tools.abtestuicreator.R;
import com.netease.tools.abtestuicreator.tools.ReplaceLongClickListenerImpl;
import com.netease.tools.abtestuicreator.util.ClickListenerUtil;

/**
 * Created by zyl06 on 2018/7/30.
 */

public class LongClickReplaceUtil {

    public static void performTraversal(View v, boolean replace) {
        Object tag = v.getTag(R.string.abtest_marked_tag);
        if ((tag != null && replace) || (tag == null && !replace)) {
            return;
        }

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            int count = vg.getChildCount();
            for (int i=0; i<count; i++) {
                View child = vg.getChildAt(i);
                performTraversal(child, replace);
            }
        }

        View.OnLongClickListener listener = ClickListenerUtil.getOnLongClickListener(v);
        if (replace) {
            if (!(listener instanceof ReplaceLongClickListenerImpl)) {
                ReplaceLongClickListenerImpl repLongClickListener = new ReplaceLongClickListenerImpl(listener);
                v.setOnLongClickListener(repLongClickListener);
                v.setTag(R.string.abtest_marked_tag, true);
            }
        } else {
            if (listener instanceof ReplaceLongClickListenerImpl) {
                ReplaceLongClickListenerImpl replaceListener = (ReplaceLongClickListenerImpl) listener;
                View.OnLongClickListener oriListener = replaceListener.getOriLongClickListener();
                v.setOnLongClickListener(oriListener);

                v.setTag(R.string.abtest_marked_tag, null);
            }
        }
    }
}
