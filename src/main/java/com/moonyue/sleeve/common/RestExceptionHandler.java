package com.moonyue.sleeve.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RestExceptionHandler {

    /*
    * Exception
    * */
    @ExceptionHandler(value = Exception.class)
    public void processException(Exception exception, HttpServletRequest request, HttpServletResponse response){
        System.out.println("hello");
    }
}
