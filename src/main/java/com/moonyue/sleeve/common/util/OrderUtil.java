package com.moonyue.sleeve.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Random;

@Component
public class OrderUtil {

    private static String[] yearCodes;

    @Value("${sleeve.year-codes}")
    public void setYearCodes(String codes){
        String[] chars = codes.split(",");
        OrderUtil.yearCodes = chars;
    }

    public static String makeOrderNo(){
        StringBuffer joiner = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        String mills = String.valueOf(calendar.getTimeInMillis());
        String micro = LocalTime.now().toString();
        String random = String.valueOf(Math.random() *1000).substring(0,2);
        joiner.append(OrderUtil.yearCodes[calendar.get(calendar.YEAR) - 2020])
                .append(Integer.toHexString(calendar.get(Calendar.MONTH)+1).toUpperCase())
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(mills.substring(mills.length()-5, mills.length()))
                .append(micro.substring(micro.length()-3, micro.length()))
                .append(random);
        return joiner.toString();
    }

}
