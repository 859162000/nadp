package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.AppEnvRelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@Repository
public interface AppEnvRelDao {
    public int insert(@Param("appId")String appId,@Param("envId")String envId);
    public List<AppEnvRelVO> getRelByAppId(AppEnvRelVO vo);
    public void updateById(AppEnvRelVO vo);
}
