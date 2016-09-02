package cn.com.netease.nadp.monitor.service.app;

import cn.com.netease.nadp.monitor.vo.AppVO;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
public interface AppService {
    /**
     * 获取app列表
     * @param name
     * @param status
     * @param pageFrom
     * @param PageCapacity
     * @return
     */
    public List<AppVO> getData(String name,String status,int pageFrom, int PageCapacity);

    /**
     * 获取数据总数给分页
     * @param name
     * @param status
     * @return
     */
    public int getDataCount(String name,String status);

    /**
     * 插入数据
     * @param map
     */
    public void insert(Map<String,Object> map);

    /**
     * 查询所有可用和不可用的
     * @param status
     */
    public List<AppVO> getAll(String status);

    /**
     *
     * @param appId
     * @return
     */
    public Map<String,Object> getRel(String appId);

    /**
     *
     * @param map
     */
    public void update(Map<String,Object> map);

    /**
     *
     * @param map
     */
    public void delete(Map<String,Object> map);
}
