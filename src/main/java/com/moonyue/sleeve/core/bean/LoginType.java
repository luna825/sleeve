package com.moonyue.sleeve.core.bean;

public enum LoginType {
    USER_WX(0, "微信登录"),
    USER_EMAIL(1, "邮箱登录"),
    USER_TEST(2,"测试登录");

    private Integer value;

    LoginType(Integer value, String description){
        this.value = value;
    }
}
