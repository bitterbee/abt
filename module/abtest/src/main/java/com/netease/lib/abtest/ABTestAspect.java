package com.netease.lib.abtest;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.netease.libs.abtestbase.ABLog;
import com.netease.libs.abtestbase.RefInvoker;
import com.netease.libs.abtestbase.ViewUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/26.
 */
@Aspect
public class ABTestAspect {

    private static final String TAG = "ABTest_apsectj";
    private static Class sRecyclerViewHolderClass;

    @After("execution(com.netease.lib.abtest.BaseABTester+.new(..)) && !within(com.netease.lib.abtest.BaseABTester)")
    public void afterBaseABTesterNew(JoinPoint joinPoint) {
        log(joinPoint);
        ((BaseABTester) joinPoint.getTarget()).initAB();
    }

    // ListView begin =====================
    @Around("execution(public android.view.View android.widget.Adapter+.getView(int, android.view.View, android.view.ViewGroup))")
    public Object aroundListViewGetViewExecution(ProceedingJoinPoint joinPoint) {
        log(joinPoint);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            if (result != null) {
                int position = (int) joinPoint.getArgs()[0];
                int type = ((ListAdapter) joinPoint.getTarget()).getItemViewType(position);
                ((View) result).setTag(R.string.abtest_tag_listview_type, type);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }
    // ListView end =====================

    // RecyclerView begin =====================
    @Around("execution(public * android.support.v7.widget.RecyclerView.Adapter+.onCreateViewHolder(android.view.ViewGroup, int))")
    public Object aroundRecycleViewCreateViewHolderExecution(ProceedingJoinPoint joinPoint) {
        log(joinPoint);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            if (result != null) {
                Class vhClass = Class.forName("android.support.v7.widget.RecyclerView$ViewHolder");
                Object itemView = RefInvoker.getFieldObject(result, vhClass, "itemView");
                ((View) itemView).setTag(R.string.abtest_tag_recycleview_holder_type, joinPoint.getArgs()[1]);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }
    // RecyclerView end =====================

    // fragment begin =====================
    @Around("execution(public android.view.View android.support.v4.app.Fragment+.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)) && !within(android.support.v4.app.Fragment)")
    public Object aroundV4FragmentOnCreateView(ProceedingJoinPoint joinPoint) {
        return doAroundFragmentOnCreateView(joinPoint);
    }

    // 如果是重复标记，没关系
    @After("execution(public void android.support.v4.app.Fragment+.onViewCreated(android.view.View, android.os.Bundle)) && !within(android.support.v4.app.Fragment)")
    public void afterV4FragmentOnViewCreated(JoinPoint joinPoint) {
        doAfterFragmentOnViewCreated(joinPoint);
    }

    @Around("execution(public android.view.View android.app.Fragment+.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)) && !within(android.app.Fragment)")
    public Object aroundFragmentOnCreateView(ProceedingJoinPoint joinPoint) {
        return doAroundFragmentOnCreateView(joinPoint);
    }

    @After("execution(public void android.app.Fragment+.onViewCreated(android.view.View, android.os.Bundle)) && !within(android.app.Fragment)")
    public void afterFragmentOnViewCreated(JoinPoint joinPoint) {
        doAfterFragmentOnViewCreated(joinPoint);
    }

    private void doAfterFragmentOnViewCreated(JoinPoint joinPoint) {
        log(joinPoint);
        View v = (View) joinPoint.getArgs()[0];
        if (v != null) {
            v.setTag(R.string.abtest_tag_environment, joinPoint.getTarget().toString());
        }
    }

    private Object doAroundFragmentOnCreateView(ProceedingJoinPoint joinPoint) {
        log(joinPoint);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            if (result instanceof View) {
                ((View) result).setTag(R.string.abtest_tag_environment, joinPoint.getTarget().getClass().getName());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return result;
    }
    // fragment end =====================

    // viewpager begin =====================
    @Around("execution(public java.lang.Object android.support.v4.view.PagerAdapter+.instantiateItem(android.view.ViewGroup, int)) && !within(android.support.v4.view.PagerAdapter)")
    public Object aroundPagerAdapter_instantiateItem(ProceedingJoinPoint joinPoint) {
        log(joinPoint);

        ViewGroup vg = (ViewGroup) joinPoint.getArgs()[0];
        int position = (int) joinPoint.getArgs()[1];

        List<View> childrenBefore = new LinkedList<>();
        try {
            childrenBefore.addAll(ViewUtil.getChildren(vg));
            vg.setTag(R.string.abtest_tag_pager_position_tmp, position);
        } catch (Exception e) {
            ABLog.e(e);
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        List<View> childrenAfter = new LinkedList<>();
        try {
            childrenAfter.addAll(ViewUtil.getChildren(vg));
            vg.setTag(R.string.abtest_tag_pager_position_tmp, null);
        } catch (Exception e) {
            ABLog.e(e);
        }

        childrenAfter.removeAll(childrenBefore);
        if (childrenAfter.size() == 1) {
            childrenAfter.get(0).setTag(R.string.abtest_tag_pager_position, position);
        }

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "aroundPagerAdapter_instantiateItem new view count " + childrenAfter.size() + "; position=" + joinPoint.getArgs()[1]);
        }

        return result;
    }

    @Around("execution(public void android.support.v4.view.PagerAdapter+.destroyItem(android.view.ViewGroup, int, java.lang.Object)) && !within(android.support.v4.view.PagerAdapter)")
    public Object aroundPagerAdapter_destroyItem(ProceedingJoinPoint joinPoint) {
        log(joinPoint);

        ViewGroup vg = (ViewGroup) joinPoint.getArgs()[0];

        List<View> childrenBefore = new LinkedList<>();
        try {
            childrenBefore.addAll(ViewUtil.getChildren(vg));
        } catch (Exception e) {
            ABLog.e(e);
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        List<View> childrenAfter = new LinkedList<>();
        try {
            childrenAfter.addAll(ViewUtil.getChildren(vg));
        } catch (Exception e) {
            ABLog.e(e);
        }

        childrenBefore.removeAll(childrenAfter);

        if (BuildConfig.DEBUG) {
            Log.d(TAG,"aroundPagerAdapter_destroyItem remove view count " + childrenBefore.size() + "; position=" + joinPoint.getArgs()[1]);
        }

        if (childrenBefore.size() == 1) {
            childrenBefore.get(0).setTag(R.string.abtest_tag_pager_position, null);
        }

        return result;
    }
    // viewpager end =====================

    private void log(JoinPoint joinPoint) {
        if (BuildConfig.DEBUG) {
            String target = joinPoint.getTarget() != null ? joinPoint.getTarget().toString() : "null";
            String signature = joinPoint.getSignature() != null ? joinPoint.getSignature().getName() : "null";
            String filename = joinPoint.getSourceLocation().getFileName();

            Log.d(TAG,target + "#" + signature + "#" + filename);
        }
    }
}
