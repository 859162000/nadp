package cn.com.netease.nadp.configuration.register;

import cn.com.netease.nadp.common.utils.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 配置获取类
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public final class Configuration {

    private static Properties MEMERY = new Properties();


    private Configuration() {
    }


    /**
     * static singleton
     */
    private static class ConfigurationBuilder {
        private static Configuration configuration = new Configuration();
    }
    /**
     * static singleton
     */
    public static Configuration getInstance() {
        return ConfigurationBuilder.configuration;
    }

    /**
     * 通过key获取配置文件
     * @param key
     * @return
     */
    public String getConfiguration(String key){
        return StringUtils.obj2String(MEMERY.get(key));
    }

    /**
     * 加载配置
     */
    protected void loadConfig(List<Map<String,String>> configuration, Map<String,ConfigurationHandler> handlerMap){
        MEMERY.clear();
        if(configuration == null || configuration.isEmpty()){
            return ;
        }
        for(Map<String,String> configurationMap : configuration){
            MEMERY.put(configurationMap.get("key_name"),configurationMap.get("value"));
        }
        if(handlerMap!=null&&!handlerMap.isEmpty()){
            for(Map.Entry<String,ConfigurationHandler> handlerEntry:handlerMap.entrySet()){
                handlerEntry.getValue().handle(MEMERY);
            }
        }
    }

    /**
     * 加载配置
     */
    protected void loadConfig(Properties configuration, Map<String,ConfigurationHandler> handlerMap){
        MEMERY.clear();
        if(configuration == null || configuration.isEmpty()){
            return ;
        }
        MEMERY.putAll(configuration);
        if(handlerMap!=null&&!handlerMap.isEmpty()){
            for(Map.Entry<String,ConfigurationHandler> handlerEntry:handlerMap.entrySet()){
                handlerEntry.getValue().handle(MEMERY);
            }
        }
    }



}