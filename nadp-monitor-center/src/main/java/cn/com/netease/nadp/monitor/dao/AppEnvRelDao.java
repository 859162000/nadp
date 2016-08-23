package cn.com.netease.nadp.monitor.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@Repository
public interface AppEnvRelDao {
    public int insert(@Param("appId")String appId,@Param("envId")String envId);
}
