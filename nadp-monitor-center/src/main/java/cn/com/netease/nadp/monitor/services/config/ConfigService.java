package cn.com.netease.nadp.monitor.services.config;

import cn.com.netease.nadp.monitor.dao.ConfigDao;
import cn.com.netease.nadp.monitor.vo.ConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * cn.com.netease.nadp.web.monitor.services.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@Service
public class ConfigService implements IConfigService{
    @Autowired
    private ConfigDao configDao;

    public List<ConfigVO> select(String key) {
        return configDao.select(key);
    }
}
