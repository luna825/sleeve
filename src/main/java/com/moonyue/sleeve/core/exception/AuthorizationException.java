package com.moonyue.sleeve.core.exception;

import com.moonyue.sleeve.core.bean.Code;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends HttpException {

    protected int httpCode = HttpStatus.UNAUTHORIZED.value();

    protected int code = Code.UN_AUTHORIZATION.getCode();

    public AuthorizationException() {
        super(Code.UN_AUTHORIZATION.getCode(), Code.UN_AUTHORIZATION.getDescription());
        super.ifDefaultMessage=true;
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(int code) {
        super(code, Code.UN_AUTHORIZATION.getDescription());
        this.code = code;
        super.ifDefaultMessage=true;
    }

    public AuthorizationException(int code, String message) {
        super(code, message);
        this.code = code;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public int getCode() {
        return code;
    }
}