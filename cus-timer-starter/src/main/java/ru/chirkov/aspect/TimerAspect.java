package ru.chirkov.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Aspect
@Slf4j
public class TimerAspect {

    /**
     * Описал пакет, в котором все классы и методы будет отслеживать мой таймер
     */
    @Pointcut("@within(ru.chirkov.annotation.Timer) || @annotation(ru.chirkov.annotation.Timer)")
    public void getTimerAction() {

    }

    @Around("getTimerAction()")
    public Object getTimerActions(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        Object action = point.proceed();
//        long end = System.currentTimeMillis();
        long workedTime = System.currentTimeMillis() - start;
//        System.out.println("start: " + start + " end: " + end);
//        System.out.println("Method : [" + point.getSignature().getName() + "], for class : " + action.getClass().getName() + "worked " + workedTime);
        log.info("className: [ " + point.getTarget().getClass().getSimpleName() + " ], Method : [ " + point.getSignature().getName() + " ] : worked [ " + workedTime + " ms ];$");
        return action;
    }

}
