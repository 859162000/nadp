package cn.com.netease.nadp.monitor.dao;

import cn.com.netease.nadp.monitor.vo.AuthVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/9/2.
 */
@Repository
public interface AuthDao {
    public List<AuthVO> select(AuthVO vo);
}
