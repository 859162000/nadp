package cn.com.netease.nadp.monitor.utils;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
public class StringUtils {
    public static String nullObj2String(Object obj){
        return obj == null ? "": obj.toString();
    }
}
