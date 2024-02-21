package ru.chirkov.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class ExecutionForReturnObject {
    @Pointcut("execution(public String *(..))")
    public void executeAction() {
    }

    @Before("executeAction()")
    public void endProceed(JoinPoint point) {
        log.info("Method : [" + point.getSignature().getName() + "], for return type : " + point.getSignature().getDeclaringType().getName() + "started (" + System.currentTimeMillis() + ")");
    }
}
