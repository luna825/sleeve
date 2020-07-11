package com.moonyue.sleeve.core.exception;

import com.moonyue.sleeve.core.bean.Code;
import org.springframework.http.HttpStatus;

/*
* 资源不存在
* */
public class NotFoundException extends HttpException {

    private int code = Code.NOT_FOUND.getCode();

    private int httpCode = HttpStatus.NOT_FOUND.value();

    public NotFoundException() {
        super(Code.NOT_FOUND.getCode(), Code.NOT_FOUND.getDescription());
        super.ifDefaultMessage=true;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int code) {
        super(code, Code.NOT_FOUND.getDescription());
        this.code = code;
        super.ifDefaultMessage=true;
    }

    public NotFoundException(int code, String message) {
        super(code, message);
        this.code = code;
    }

    @Override
    public int getHttpCode() {
        return this.httpCode;
    }

    @Override
    public int getCode() {
        return this.code;
    }

}
