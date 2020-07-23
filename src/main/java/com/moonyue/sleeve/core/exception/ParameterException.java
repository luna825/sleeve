package com.moonyue.sleeve.core.exception;

import com.moonyue.sleeve.core.bean.Code;
import org.springframework.http.HttpStatus;

public class ParameterException extends HttpException {

    private int code = Code.PARAMETER_ERROR.getCode();

    private int httpCode = HttpStatus.BAD_REQUEST.value();

    public ParameterException() {
        super(Code.PARAMETER_ERROR.getCode(), Code.PARAMETER_ERROR.getDescription());
        super.ifDefaultMessage=true;
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(int code) {
        super(code, Code.PARAMETER_ERROR.getDescription());
        this.code = code;
        super.ifDefaultMessage=true;
    }

    public ParameterException(int code, String message) {
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
