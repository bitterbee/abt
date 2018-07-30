package com.netease.lib.abtest;

import com.netease.lib.abtest.util.ABLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by zyl06 on 2018/7/26.
 */
@Aspect
public class AspectABTester {

    @After("execution(com.netease.lib.abtest.BaseABTester+.new(..)) && !within(com.netease.lib.abtest.BaseABTester)")
    public void afterMethodExecution(JoinPoint joinPoint) {
        String target = joinPoint.getTarget() != null ? joinPoint.getTarget().toString() : "null";
        String signature = joinPoint.getSignature() != null ? joinPoint.getSignature().getName() : "null";
        String filename = joinPoint.getSourceLocation().getFileName();

        ABLog.i("after->" + target + "#" + signature + "#" + filename);

        ((BaseABTester) joinPoint.getTarget()).initAB();
    }
}
