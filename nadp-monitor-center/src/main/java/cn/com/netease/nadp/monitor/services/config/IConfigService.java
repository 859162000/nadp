package cn.com.netease.nadp.monitor.services.config;

import cn.com.netease.nadp.common.vo.ConfigVO;

import java.util.List;

/**
 * cn.com.netease.nadp.web.monitor.services.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
public interface IConfigService {
    /**
     * 查询配置
     * @param key
     * @return
     */
    public List<ConfigVO> select(String key);

    /**
     * 更新配置
     * @param id
     * @param value
     */
    public void update(int id,String value);

    /**
     * 插入配置
     * @param key
     * @param value
     */
    public void insert(String key,String value,String description);

    /**
     * 发布配置数据
     * @param list
     * @throws Exception
     */
    public void publicConfig(List<ConfigVO> list)throws Exception;
}
