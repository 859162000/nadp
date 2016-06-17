package cn.com.netease.nadp.monitor.services.config;

import java.util.Map;

/**
 * cn.com.netease.nadp.web.monitor.services.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
public interface IConfigService {
    /**
     * 从数据库获取数据
     * @return
     */
    public Map<String,String> loadConfigFromDB();
}
