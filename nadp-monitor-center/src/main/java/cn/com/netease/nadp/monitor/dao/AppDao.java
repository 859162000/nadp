package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.AppVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@Repository
public interface AppDao {
    /**
     * 获取app列表
     * @param name
     * @param status
     * @param pageFrom
     * @param PageCapacity
     * @return
     */
    public List<AppVO> select(@Param("name") String name,@Param("appKey")String appKey,@Param("status") String status, @Param("pageFrom")int pageFrom, @Param("PageCapacity")int PageCapacity);

    /**
     * 获取数据总量给分页
     * @param name
     * @param status
     * @return
     */
    public int selectDataCount(@Param("name") String name,@Param("status") String status);

    /**
     * 插入数据
     * @param vo
     */
    public void insert(AppVO vo);

    public List<AppVO> selectAll(@Param("status") String status);
}
