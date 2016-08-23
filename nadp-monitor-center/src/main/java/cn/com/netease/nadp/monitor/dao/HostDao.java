package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.HostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@Repository
public interface HostDao {
    /**
     * 获取数据
     * @param host
     * @param status
     * @param pageFrom
     * @param PageCapacity
     * @return
     */
    public List<HostVO> select(@Param("host") String host, @Param("status") String status, @Param("pageFrom")int pageFrom, @Param("PageCapacity")int PageCapacity);

    /**
     * 获取数据总数给分页
     * @param host
     * @param status
     * @return
     */
    public int selectDataCount(@Param("host") String host, @Param("status") String status);

    /**
     * 插入数据
     * @param vo
     */
    public void insert(HostVO vo);

}
