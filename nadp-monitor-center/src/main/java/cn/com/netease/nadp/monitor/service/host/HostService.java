package cn.com.netease.nadp.monitor.service.host;

import cn.com.netease.nadp.monitor.dao.HostDao;
import cn.com.netease.nadp.monitor.vo.HostVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
public interface HostService {
    /**
     * 获取数据
     * @return
     */
    public List<HostVO> getData(String host, String status, int pageFrom, int PageCapacity);

    /**
     * 获取数据给分页
     * @param host
     * @param status
     * @return
     */
    public int getDataCount(String host, String status);

    /**
     * 插入数据
     * @param map
     */
    public void insert(Map<String,Object> map);

    /**
     *
     * @param map
     * @return
     */
    public Map<String,Object> getRel(Map<String,String> map);

    /**
     *
     * @param map
     */
    public void update(Map<String,Object> map);

    /**
     *
     * @param map
     */
    public void delete(Map<String,String> map);
}
