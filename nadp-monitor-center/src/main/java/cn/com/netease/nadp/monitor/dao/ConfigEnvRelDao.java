package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.ConfigurationEnvRelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/16.
 */
@Repository
public interface ConfigEnvRelDao {
    public void insert(@Param("configId")String configId,@Param("envId")String envId);

    public List<ConfigurationEnvRelVO> getRel(ConfigurationEnvRelVO vo);

    public void updateRelStatusByConfigId(@Param("configId")String configId,@Param("status")String status);
}
