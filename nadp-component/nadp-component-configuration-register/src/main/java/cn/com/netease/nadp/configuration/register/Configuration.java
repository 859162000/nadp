package cn.com.netease.nadp.configuration.register;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置获取类
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public final class Configuration {


    private static Map<String,String> map = new HashMap<String ,String>(100);

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
        return map.get(key);
    }

    /**
     * 加载配置
     */
    protected void loadConfig(List<Map<String,String>> configuration, Map<String,ConfigurationHandler> handlerMap){
        map.clear();
        if(configuration == null || configuration.isEmpty()){
            return ;
        }
        for(Map<String,String> configurationMap : configuration){
            map.put(configurationMap.get("key_name"),configurationMap.get("value"));
        }
        if(handlerMap!=null&&!handlerMap.isEmpty()){
            for(Map.Entry<String,ConfigurationHandler> handlerEntry:handlerMap.entrySet()){
                handlerEntry.getValue().handle(map);
            }
        }
    }

}
