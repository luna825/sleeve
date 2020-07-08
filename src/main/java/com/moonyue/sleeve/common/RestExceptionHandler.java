package com.moonyue.sleeve.common;


import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.common.configuration.CodeMessageConfiguration;
import com.moonyue.sleeve.core.bean.Code;
import com.moonyue.sleeve.core.exception.HttpException;
import com.moonyue.sleeve.vo.UnifyResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.moonyue.sleeve.common.util.RequestUtil.getSimpleRequest;

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

    /*
    * HttpException
    */
    @ExceptionHandler({HttpException.class})
    public UnifyResponseVO processException(HttpException exception, HttpServletRequest request, HttpServletResponse response){
        UnifyResponseVO result = new UnifyResponseVO();
        result.setRequest(getSimpleRequest(request));
        int code = exception.getCode();
        boolean ifDefaultMessage = exception.ifDefaultMessage();
        result.setCode(code);
        response.setStatus(exception.getHttpCode());
        String errorMessage = CodeMessageConfiguration.getMessage(code);
        if(StrUtil.isBlank(errorMessage) || !ifDefaultMessage){
            result.setMessage(exception.getMessage());
        }else{
            result.setMessage(errorMessage);
        }
        return result;
    }
}
