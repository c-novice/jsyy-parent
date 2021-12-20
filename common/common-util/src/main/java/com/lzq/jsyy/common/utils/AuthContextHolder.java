package com.lzq.jsyy.common.utils;

import com.lzq.jsyy.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前用户信息工具类
 *
 * @author lzq
 */
public class AuthContextHolder {
    /**
     * 获取当前用户id
     *
     * @param request
     * @return
     */
    public static String getUserId(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取userid
        return JwtHelper.getUserId(token);
    }

    /**
     * 获取当前用户名称
     *
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        //jwt从token获取username
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
