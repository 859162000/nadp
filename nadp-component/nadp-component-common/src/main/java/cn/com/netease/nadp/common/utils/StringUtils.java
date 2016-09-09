package cn.com.netease.nadp.common.utils;

/**
 * Created by bjbianlanzhou on 2016/9/6.
 */
public class StringUtils {
    public static String obj2String(Object obj){
        if(obj != null){
            return obj.toString();
        }
        return "";
    }
}
