package com.moonyue.sleeve.core.exception;

import com.moonyue.sleeve.core.bean.Code;
import com.moonyue.sleeve.core.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException implements BaseResponse {
    protected int code = Code.INTERNAL_SERVER_ERROR.getCode();
    protected int httpCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 是否是默认消息
     * true： 没有通过构造函数传入 message
     * false：通过构造函数传入了 message
     *
     * 没有用 isDefaultMessage 是因为和部分 rpc 框架存在兼容问题
     */
    protected boolean ifDefaultMessage = true;

    public HttpException(){
        super(Code.INTERNAL_SERVER_ERROR.getZhDescription());
    }

    public HttpException(String message){
        super(message);
        this.ifDefaultMessage = false;
    }

    public HttpException(int code){
        super(Code.INTERNAL_SERVER_ERROR.getZhDescription());
        this.code = code;
    }

    public HttpException(int code, int httpCode) {
        super(Code.INTERNAL_SERVER_ERROR.getZhDescription());
        this.httpCode = httpCode;
        this.code = code;
    }

    public HttpException(int code, String message) {
        super(message);
        this.code = code;
        this.ifDefaultMessage = false;
    }

    public HttpException(int code, String message, int httpCode) {
        super(message);
        this.code = code;
        this.httpCode = httpCode;
        this.ifDefaultMessage = false;
    }

    @Override
    public int getHttpCode() {
        return this.httpCode;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    public boolean ifDefaultMessage(){
        return this.ifDefaultMessage;
    }
}
