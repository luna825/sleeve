package com.moonyue.sleeve.common.util;

import com.moonyue.sleeve.bo.PageCounter;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count){
        int page = start / count;
        return PageCounter.builder()
                .page(page)
                .size(count)
                .build();
    }
}
