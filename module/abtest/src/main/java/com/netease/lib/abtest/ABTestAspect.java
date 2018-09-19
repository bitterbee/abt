package com.netease.lib.abtest;

import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;

import com.netease.libs.abtestbase.RefInvoker;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

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

    ////////////////////////////////
    // ListView begin =====================
    @Around("execution(public android.view.View android.widget.ListAdapter+.getView(int, android.view.View, android.view.ViewGroup))")
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
    ////////////////////////////////

    ////////////////////////////////
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
    ////////////////////////////////

    ////////////////////////////////
    // fragment begin =====================
    @Around("execution(public android.view.View android.support.v4.app.Fragment+.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)) && !within(android.support.v4.app.Fragment)")
    public Object aroundV4FragmentOnCreateView(ProceedingJoinPoint joinPoint) {
        return doAroundFragmentOnCreateView(joinPoint);
    }

    // 如果是重复标记，没关系
    @After("execution(public void android.support.v4.app.Fragment+.onViewCreated(android.view.View, android.os.Bundle)) && !within(android.support.v4.app.Fragment)")
    public void aroundV4FragmentOnViewCreated(JoinPoint joinPoint) {
        doAroundFragmentOnViewCreated(joinPoint);
    }

    @Around("execution(public android.view.View android.app.Fragment+.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)) && !within(android.app.Fragment)")
    public Object aroundFragmentOnCreateView(ProceedingJoinPoint joinPoint) {
        return doAroundFragmentOnCreateView(joinPoint);
    }

    @After("execution(public void android.app.Fragment+.onViewCreated(android.view.View, android.os.Bundle)) && !within(android.app.Fragment)")
    public void aroundFragmentOnViewCreated(JoinPoint joinPoint) {
        doAroundFragmentOnViewCreated(joinPoint);
    }

    private void doAroundFragmentOnViewCreated(JoinPoint joinPoint) {
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
    ////////////////////////////////

    private void log(JoinPoint joinPoint) {
        String target = joinPoint.getTarget() != null ? joinPoint.getTarget().toString() : "null";
        String signature = joinPoint.getSignature() != null ? joinPoint.getSignature().getName() : "null";
        String filename = joinPoint.getSourceLocation().getFileName();

        Log.d(TAG,target + "#" + signature + "#" + filename);
    }
}
