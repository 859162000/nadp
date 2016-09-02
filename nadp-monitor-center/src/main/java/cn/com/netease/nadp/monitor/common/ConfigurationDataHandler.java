package cn.com.netease.nadp.monitor.common;

import cn.com.netease.nadp.configuration.register.ConfigurationHandler;
import cn.com.netease.nadp.zookeeper.ZkDataHandler;
import cn.com.netease.nadp.zookeeper.ZkManager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/22.
 */
public class ConfigurationDataHandler implements ConfigurationHandler {

    public void handle(Map<String, String> configurations) {
        for(Map.Entry<String, String> entry : configurations.entrySet()){
            System.out.println("keys : " + entry.getKey() + " , " + "value : " + entry.getValue());
        }
    }
}
