package com.moonyue.sleeve.common;


import cn.hutool.core.util.StrUtil;
import com.moonyue.sleeve.common.configuration.CodeMessageConfiguration;
import com.moonyue.sleeve.core.bean.Code;
import com.moonyue.sleeve.core.exception.HttpException;
import com.moonyue.sleeve.vo.UnifyResponseVO;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import java.net.BindException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.moonyue.sleeve.common.util.RequestUtil.getSimpleRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    /*
    * Exception
    * */
    @ExceptionHandler(value = Exception.class)
    public UnifyResponseVO processException(Exception exception, HttpServletRequest request, HttpServletResponse response){
        exception.printStackTrace();
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

    /*
    *MethodArgumentNotValidException
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public UnifyResponseVO processException(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response){
        BindingResult bindResult = exception.getBindingResult();
        List<ObjectError> errors = bindResult.getAllErrors();
        Map<String, Object> msg = new HashMap<>();
        UnifyResponseVO result = new UnifyResponseVO();
        errors.forEach(error ->{
            if(error instanceof FieldError){
                FieldError fieldError = (FieldError) error;
                msg.put(StrUtil.toUnderlineCase(fieldError.getField()), fieldError.getDefaultMessage());
            }else{
                msg.put(StrUtil.toUnderlineCase(error.getObjectName()), error.getDefaultMessage());
            }
        });
        result.setRequest(getSimpleRequest(request));
        result.setMessage(msg);
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
    * ConstraintViolationException
    * */
    @ExceptionHandler(ConstraintViolationException.class)
    public UnifyResponseVO processException(ConstraintViolationException exception, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> msg =new HashMap<>();
        exception.getConstraintViolations().forEach(error -> {
            String message = error.getMessage();
            String path = error.getPropertyPath().toString();
            msg.put(StrUtil.toUnderlineCase(path), message);
        });
        UnifyResponseVO result = new UnifyResponseVO();
        result.setRequest(getSimpleRequest(request));
        result.setCode(Code.PARAMETER_ERROR.getCode());
        result.setMessage(msg);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * MissingServletRequestParameterException
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public UnifyResponseVO processException(MissingServletRequestParameterException exception, HttpServletRequest request, HttpServletResponse response) {
        UnifyResponseVO result = new UnifyResponseVO();
        result.setRequest(getSimpleRequest(request));

        String errorMessage = CodeMessageConfiguration.getMessage(10150);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(errorMessage + exception.getParameterName());
        }
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * HttpMessageNotReadableException
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public UnifyResponseVO processException(HttpMessageNotReadableException exception, HttpServletRequest request, HttpServletResponse response) {
        exception.printStackTrace();
        UnifyResponseVO result = new UnifyResponseVO();
        result.setRequest(getSimpleRequest(request));
        String errorMessage = CodeMessageConfiguration.getMessage(10170);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(errorMessage);
        }
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * HttpMessageNotReadableException
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public UnifyResponseVO processException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request, HttpServletResponse response) {
        UnifyResponseVO result = new UnifyResponseVO();
        result.setRequest(getSimpleRequest(request));
        String errorMessage = CodeMessageConfiguration.getMessage(10080);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(errorMessage);
        }
        result.setCode(Code.METHOD_NOT_ALLOWED.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * MethodArgumentTypeMismatchException
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public UnifyResponseVO processException(MethodArgumentTypeMismatchException exception, HttpServletRequest request, HttpServletResponse response) {
        UnifyResponseVO result = new UnifyResponseVO();
        result.setRequest(getSimpleRequest(request));
        String errorMessage = CodeMessageConfiguration.getMessage(10160);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(exception.getValue() + errorMessage);
        }
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

}
