package cn.com.netease.nadp.monitor.service.config;

import cn.com.netease.nadp.monitor.vo.ConfigurationVO;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public interface ConfigService {
    /**
     * 插入配置
     * @param map
     */
    public void insert(Map<String,Object> map);

    /**
     * 获取配置数据
     * @param name
     * @param type
     * @param status
     * @param pageFrom
     * @param PageCapacity
     * @return
     */
    public List<ConfigurationVO> getData(String id,String name,String type, String status,String env,String app, int pageFrom, int PageCapacity);

    /**
     * 获取配置条数
     * @param name
     * @param type
     * @param status
     * @return
     */
    public int getDataCount(String name,String type, String status,String env,String app);

    /**
     * 通过ID删除配置
     * @param id
     */
    public int deleteById(String id);

    /**
     *
     * @param id
     */
    public void updateById(String id);

    /**
     * 获取联系
     * @param id
     * @return
     */
    public Map<String,Object> getRel(String id);

    /**
     * 更新
     * @param map
     */
    public void update(Map<String,Object> map);


}
