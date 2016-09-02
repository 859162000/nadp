package cn.com.netease.nadp.monitor.service.center;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/22.
 */
public interface CenterService {
    /**
     * 获取注册中心ZK地址
     * @param ip
     * @param appKey
     * @return
     */
    public Map<String,Object> getRegistCenter(String ip, String appKey);

    /**
     * 获取配置
     * @param appKey
     * @param ip
     * @return
     */
    public List<Map<String,String>> getConfig(String appKey, String ip,String type);

    /**
     * 通知
     * @param appId
     * @param envId
     */
    public void notify(String appId,String envId,String appKey);
}
