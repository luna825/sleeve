package com.moonyue.sleeve.core.exception;

import com.moonyue.sleeve.core.bean.Code;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {
    private int code = Code.FORBIDDEN.getCode();

    private int httpCode = HttpStatus.FORBIDDEN.value();

    public ForbiddenException() {
        super(Code.NOT_FOUND.getCode(), Code.FORBIDDEN.getDescription());
        super.ifDefaultMessage=true;
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(int code) {
        super(code, Code.FORBIDDEN.getDescription());
        this.code = code;
        super.ifDefaultMessage=true;
    }

    public ForbiddenException(int code, String message) {
        super(code, message);
        this.code = code;
    }
}
