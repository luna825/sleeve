package com.moonyue.sleeve.common.util;

import com.moonyue.sleeve.bo.PageCounter;

import java.util.Date;

public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count){
        int page = start / count;
        return PageCounter.builder()
                .page(page)
                .size(count)
                .build();
    }

    public static Boolean isInTimeLine(Date date, Date start, Date end){
        long time = date.getTime();
        long startTime = start.getTime();
        long endTime = end.getTime();
        return time > startTime && time < endTime;
    }
}
