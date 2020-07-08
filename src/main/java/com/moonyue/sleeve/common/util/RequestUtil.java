package com.moonyue.sleeve.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
* 请求工具类
* */
public class RequestUtil {

    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            return null;
        }

        if(requestAttributes instanceof ServletRequestAttributes){
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }else{
            return null;
        }
    }

    /**
     * 获得请求 url
     *
     * @return url
     */
    public static String getRequestUrl() {
        HttpServletRequest request = RequestUtil.getRequest();
        if (request == null) {
            return null;
        }
        return request.getServletPath();
    }

    /**
     * 获得请求简略信息
     *
     * @param request 请求
     * @return 简略信息
     */
    public static String getSimpleRequest(HttpServletRequest request) {
        return request.getMethod() + " " + request.getServletPath();
    }

    /**
     * 获得请求简略信息
     *
     * @return 简略信息
     */
    public static String getSimpleRequest() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getMethod() + " " + request.getServletPath();
    }
}
