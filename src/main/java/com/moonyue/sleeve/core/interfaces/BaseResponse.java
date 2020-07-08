package com.moonyue.sleeve.core.interfaces;

/*
* 基础返回接口
* 实现三个方法
* */
public interface BaseResponse {
    String getMessage();

    int getHttpCode();

    int getCode();
}
