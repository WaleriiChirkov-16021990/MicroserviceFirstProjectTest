package ru.chirkov.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Aspect
@Slf4j
public class TimerAspect {


    @Value("${cus.timers.enabled:true}")
    private boolean enabled;

    @Value("${cus.timers.logLevel:info}")
    private String logLevel;

    @Pointcut("@within(ru.chirkov.annotation.Timer) || @annotation(ru.chirkov.annotation.Timer)")
    public void getTimerAction() {
    }

    @Around("getTimerAction()")
    public Object getTimerActions(ProceedingJoinPoint point) throws Throwable {
        if (!enabled) return point.proceed();
        long start = System.currentTimeMillis();
        Object action = point.proceed();
        long workedTime = System.currentTimeMillis() - start;

        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            log.debug("className: [ " + point.getTarget().getClass().getSimpleName() + " ], Method : [ "
                    + point.getSignature().getName() + " ] : worked [ " + workedTime + " ms ]");
        } else {
            log.info("className: [ " + point.getTarget().getClass().getSimpleName() + " ], Method : [ "
                    + point.getSignature().getName() + " ] : worked [ " + workedTime + " ms ]");
        }
        return action;
    }
}