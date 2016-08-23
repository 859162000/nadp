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
    public List<ConfigurationVO> getData(String name,String type, String status, int pageFrom, int PageCapacity);

    /**
     * 获取配置条数
     * @param name
     * @param type
     * @param status
     * @return
     */
    public int getDataCount(String name,String type, String status);

    /**
     * 通过ID删除配置
     * @param id
     */
    public int deleteById(String id);

    /**
     * 通过ID进行更新
     * @param id
     * @param value
     * @param type
     * @param status
     */
    public void updateById(String id,String value,String type,String status);


}
