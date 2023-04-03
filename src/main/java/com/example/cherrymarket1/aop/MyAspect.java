package com.example.cherrymarket1.aop;

import com.example.cherrymarket1.util.CustomResponse;
import com.example.cherrymarket1.util.CustomStatus;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
    @Around("Pointcuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint){

        Object result =null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            result = new CustomResponse( CustomStatus.EXCEPTION);

        }
        return result;
    }

}
