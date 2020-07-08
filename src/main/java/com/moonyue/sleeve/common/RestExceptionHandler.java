package com.moonyue.sleeve.common;

import com.moonyue.sleeve.core.bean.Code;
import com.moonyue.sleeve.vo.UnifyResponseVO;
import org.springframework.http.HttpStatus;
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
    public UnifyResponseVO processException(Exception exception, HttpServletRequest request, HttpServletResponse response){
        UnifyResponseVO result = new UnifyResponseVO(Code.INTERNAL_SERVER_ERROR.getCode(),
                Code.INTERNAL_SERVER_ERROR.getZhDescription());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }
}
