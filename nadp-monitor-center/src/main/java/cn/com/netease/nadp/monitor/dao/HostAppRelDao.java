package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.HostAppRelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/12.
 * Description
 */
@Repository
public interface HostAppRelDao {
    public void insert(HostAppRelVO vo);
    public List<HostAppRelVO> select(@Param("hostId")String hostId, @Param("appId")String appId);
}
