package cn.com.netease.nadp.monitor.services.config;

import cn.com.netease.nadp.common.common.Config;
import cn.com.netease.nadp.common.common.Constants;
import cn.com.netease.nadp.common.utils.AESHelper;
import cn.com.netease.nadp.common.utils.SerializeUtils;
import cn.com.netease.nadp.common.vo.ConfigVO;
import cn.com.netease.nadp.monitor.dao.ConfigDao;
import cn.com.netease.nadp.monitor.utils.ZKUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * cn.com.netease.nadp.web.monitor.services.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@Service
public class ConfigService implements IConfigService{
    @Autowired
    private ConfigDao configDao;

    /**
     * 查询配置
     * @param key
     * @return
     */
    public List<ConfigVO> select(String key) {
        return configDao.select(key);
    }

    /**
     * 更新配置
     * @param id
     * @param value
     */
    public void update(int id,String value){ configDao.update(id,value); }

    /**
     * 插入配置
     * @param key
     * @param value
     */
    public void insert(String key,String value,String description){ configDao.insert(key, value,description);}

    public void publicConfig(List<ConfigVO> list)throws Exception{
        byte[] data = SerializeUtils.serialize(list);
        ZKUtils.getZkUtils().updateNodeData(data,Constants.CONFIG_CENTER);
    }
}
