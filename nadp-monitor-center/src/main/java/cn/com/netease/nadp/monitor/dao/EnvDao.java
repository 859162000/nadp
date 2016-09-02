package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.EnvVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/10.
 * Description
 */
@Repository
public interface EnvDao {

    public List<EnvVO> select(@Param("envId")String envId,@Param("name")String name, @Param("status")String status, @Param("pageFrom")int pageFrom, @Param("PageCapacity")int PageCapacity);

    public int selectDataCount(@Param("name")String name, @Param("status")String status);

    public int insert(@Param("name")String name, @Param("description")String description,@Param("zkAddress")String zkAddress);

    public List<EnvVO> selectAll(@Param("status")String status);

    public void updateById(EnvVO vo);
}
