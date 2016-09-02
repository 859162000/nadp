package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.HostEnvAppRelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/12.
 * Description
 */
@Repository
public interface HostEnvAppRelDao {
    public void insert(HostEnvAppRelVO vo);

    public List<HostEnvAppRelVO> select(@Param("appId")String appId, @Param("hostId")String hostId,@Param("envId")String envId,@Param("status")String status);

    public void updateByHostId(HostEnvAppRelVO vo);
}
