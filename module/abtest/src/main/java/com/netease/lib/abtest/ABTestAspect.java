package com.netease.lib.abtest;

import android.util.Log;
import android.view.View;

import com.netease.libs.abtestbase.ABLog;
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

    @After("execution(com.netease.lib.abtest.BaseABTester+.new(..)) && !within(com.netease.lib.abtest.BaseABTester)")
    public void afterMethodExecution(JoinPoint joinPoint) {
        String target = joinPoint.getTarget() != null ? joinPoint.getTarget().toString() : "null";
        String signature = joinPoint.getSignature() != null ? joinPoint.getSignature().getName() : "null";
        String filename = joinPoint.getSourceLocation().getFileName();

        ABLog.i("after->" + target + "#" + signature + "#" + filename);

        ((BaseABTester) joinPoint.getTarget()).initAB();
    }

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

    private void log(JoinPoint joinPoint) {
        String target = joinPoint.getTarget() != null ? joinPoint.getTarget().toString() : "null";
        String signature = joinPoint.getSignature() != null ? joinPoint.getSignature().getName() : "null";
        String filename = joinPoint.getSourceLocation().getFileName();

        Log.d(TAG,target + "#" + signature + "#" + filename);
    }
}
