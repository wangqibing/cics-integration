package com.ccic.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * com.ccic.controller.*.*(..))&&(@annotation(org.springframework.web.bind.annotation.PostMapping)||@annotation(org.springframework.web.bind.annotation.GetMapping))&&(@target(org.springframework.web.bind.annotation.RestController)||@target(org.springframework.stereotype.Controller))")
    private void log() {
    }

    @Before("log()")

    public void beforeMethod(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        log.info("LOG--- The method={},begins with={}", methodName, Arrays.asList(args));

    }

//    @After("log()")
//
//    public void afterMethod(JoinPoint joinPoint) {
//
//        String methodName = joinPoint.getSignature().getName();
//
//        log.info("The method={},ends", methodName);
//
//    }

    @AfterReturning(pointcut = "log()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("LOG--- The method={},ends with={}", methodName, result);
    }

    @AfterThrowing(pointcut = "log()", throwing = "e")

    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        String methodName = joinPoint.getSignature().getName();
        log.error("LOG--- The method={},occurs excetion:={}", methodName, e);
    }
}
