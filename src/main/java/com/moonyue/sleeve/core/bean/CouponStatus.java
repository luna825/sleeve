package com.moonyue.sleeve.core.bean;

import java.util.stream.Stream;

public enum CouponStatus {

    AVAILABLE(1, "可以使用,未过期"),
    USED(2, "已使用"),
    EXPIRED(3, "未使用，已过期");

    private Integer value;;

    CouponStatus(Integer value, String description){
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }

    public static CouponStatus toType(int value) {
        return Stream.of(CouponStatus.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }
}
