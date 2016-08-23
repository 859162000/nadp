package cn.com.netease.nadp.monitor.service.config;

import cn.com.netease.nadp.common.Constants;
import cn.com.netease.nadp.monitor.dao.*;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import cn.com.netease.nadp.monitor.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
@Service("ConfigService")
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private ConfigAppRelDao configAppRelDao;
    @Autowired
    private ConfigEnvRelDao configEnvRelDao;

    public void insert(Map<String,Object> map) {
        ConfigurationVO vo = new ConfigurationVO();
        vo.setKeyName(StringUtils.nullObj2String(map.get("name")));
        vo.setValue(StringUtils.nullObj2String(map.get("value")));
        vo.setType(StringUtils.nullObj2String(map.get("type")));
        vo.setDescription(StringUtils.nullObj2String(map.get("description")));
        configDao.insert(vo);
        List<String> envs = (ArrayList<String>)map.get("envArr");
        List<String> apps = (ArrayList<String>)map.get("appArr");
        for(String appId:apps){
            configAppRelDao.insert(vo.getId()+"",appId);
        }
        for(String envId:envs){
            configEnvRelDao.insert(vo.getId()+"",envId);
        }
    }

    public List<ConfigurationVO> getData(String name,String type, String status, int pageFrom, int pageCapacity) {
        return configDao.select(name,type,status,pageFrom,pageCapacity);
    }

    public int getDataCount(String name,String type, String status){
        return configDao.selectDataCount(name,type,status);
    }

    public int deleteById(String id){
        return configDao.deleteById(id);
    }

    public void updateById(String id,String value,String type,String status){
        configDao.updateById(id,value,type,status);
    }


}
