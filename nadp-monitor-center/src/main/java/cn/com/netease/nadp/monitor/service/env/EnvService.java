package cn.com.netease.nadp.monitor.service.env;

import cn.com.netease.nadp.monitor.vo.EnvVO;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/10.
 * Description
 */
public interface EnvService {
    public List<EnvVO> getData(String name, String status, int pageFrom, int PageCapacity);

    public List<EnvVO> getAll(String status);

    public int getDataCount(String name, String status);

    public int insert(String name,String description,String zkAddress);

    public void update(Map<String,String> map);

    public void delete(Map<String,String> map);
}
