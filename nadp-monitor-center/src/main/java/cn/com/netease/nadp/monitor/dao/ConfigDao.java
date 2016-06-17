package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.ConfigVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * cn.com.netease.nadp.web.monitor.dao
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@Repository
public interface ConfigDao {
    public List<ConfigVO> selectAll();
}
