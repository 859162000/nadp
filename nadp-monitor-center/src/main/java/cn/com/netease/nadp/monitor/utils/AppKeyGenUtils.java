package cn.com.netease.nadp.monitor.utils;

import java.util.UUID;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
public class AppKeyGenUtils {

    private AppKeyGenUtils(){};
    public static AppKeyGenUtils getInstance(){
        return AppKeyGenUtilsBuilder.instance;
    }
    private static class AppKeyGenUtilsBuilder{
        private static AppKeyGenUtils instance = new AppKeyGenUtils();
    }

    public String genAppKey(){
        return UUID.randomUUID().toString();
    }

}
