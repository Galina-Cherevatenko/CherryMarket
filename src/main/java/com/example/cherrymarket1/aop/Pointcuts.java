package com.example.cherrymarket1.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* com.example.cherrymarket1.controllers.OrderController.add*(..))" )
    public void allAddMethods(){}


}
