package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.ConfigurationAppRelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/16.
 */
@Repository
public interface ConfigAppRelDao {
    public void insert(@Param("configId")String configId,@Param("appId")String appId);

    public List<ConfigurationAppRelVO> getRel(ConfigurationAppRelVO vo);

    public void updateRelStatusByConfigId(@Param("configId")String configId,@Param("status")String status);
}
