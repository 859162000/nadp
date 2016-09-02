package cn.com.netease.nadp.monitor.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by bianlanzhou on 16/8/15.
 * Description
 */
public class NetUtils {
    public static String getRemoteIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if(ip!=null&&ip.indexOf(",")>0){
            ip = ip.split(",")[1];
        }
        return ip;
    }
}
