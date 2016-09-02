package cn.com.netease.nadp.configuration.register;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/18.
 */
public interface ConfigurationHandler {
    public void handle(Map<String,String> configurations);
}
