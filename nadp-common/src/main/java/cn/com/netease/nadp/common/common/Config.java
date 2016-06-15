package cn.com.netease.nadp.common.common;

import cn.com.netease.nadp.common.utils.AESHelper;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;

/**
 * Created by bianlanzhou on 16/6/14.
 * Description
 */
public class Config extends PropertyPlaceholderConfigurer{
    private String url ;
    private int port;

    /**
     * 构造函数
     * @param url
     * @param port
     */
    public Config(String url,int port){
        try {
            url = AESHelper.aesDecrypt(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    public void getData(){

    }
}
