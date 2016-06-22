package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.common.vo.ConfigVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * cn.com.netease.nadp.web.monitor.dao
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@Repository
public interface ConfigDao {
    public List<ConfigVO> select(@Param("key") String key);
    public int update(@Param("id")int id,@Param("value")String value);
    public void insert(@Param("key")String key,@Param("value")String value,@Param("description")String description);
}
