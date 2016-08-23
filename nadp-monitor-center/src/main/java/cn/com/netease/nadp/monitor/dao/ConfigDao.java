package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.ConfigurationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * cn.com.netease.nadp.web.monitor.dao
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@Repository
public interface ConfigDao {
    /**
     * 插入配置数据
     * @param vo
     * @return
     */
    public int insert(ConfigurationVO vo);

    /**
     * 查询
     * @param type
     * @param status
     * @param pageFrom
     * @param PageCapacity
     * @return
     */
    public List<ConfigurationVO> select(@Param("name")String name,@Param("type")String type, @Param("status")String status, @Param("pageFrom")int pageFrom, @Param("PageCapacity")int PageCapacity);

    /**
     * 查询数据总量（FOR PAGINATION）
     * @param type
     * @param status
     * @return
     */
    public int selectDataCount(@Param("name")String name,@Param("type")String type,@Param("status")String status);

    /**
     * 删除
     * @param id
     */
    public int deleteById(@Param("id")String id);

    /**
     * 更新
     * @param id
     * @param value
     * @param type
     * @param status
     */
    public void updateById(@Param("id")String id,@Param("value")String value,@Param("type")String type,@Param("status")String status);

    /**
     * 通过APP和环境查询配置
     * @param appId
     * @param envId
     * @return
     */
    public List<Map<String,String>> getConfiguration(@Param("appId")String appId,@Param("envId")String envId,@Param("type")String type);
}
