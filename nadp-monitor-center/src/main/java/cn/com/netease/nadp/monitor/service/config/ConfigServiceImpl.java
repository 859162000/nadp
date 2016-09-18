package cn.com.netease.nadp.monitor.service.config;

import cn.com.netease.nadp.common.Constants;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.dao.*;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import cn.com.netease.nadp.monitor.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT)
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

    public List<ConfigurationVO> getData(String id,String name,String type, String status,String env,String app, int pageFrom, int pageCapacity) {
        return configDao.select(id,name,type,status,env,app,pageFrom,pageCapacity);
    }

    public int getDataCount(String name,String type, String status,String env,String app){
        return configDao.selectDataCount(name,type,status,env,app);
    }

    public int deleteById(String id){
        return configDao.deleteById(id);
    }

    public void updateById(String idStr){
        int id ;
        try{
            id = Integer.valueOf(idStr);
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        ConfigurationVO vo = new ConfigurationVO();
        vo.setId(id);
        vo.setStatus(Constant.STATUS_UNUSEFUL);
        configDao.updateById(vo);
    }

    public Map<String,Object> getRel(String id){
        Map<String,Object> rtn = new HashMap<String, Object>(2);
        ConfigurationAppRelVO configurationAppRelVO = new ConfigurationAppRelVO();
        configurationAppRelVO.setConfigId(id);
        configurationAppRelVO.setStatus(Constant.STATUS_USEFUL);
        ConfigurationEnvRelVO configurationEnvRelVO = new ConfigurationEnvRelVO();
        configurationEnvRelVO.setConfigId(id);
        configurationEnvRelVO.setStatus(Constant.STATUS_USEFUL);
        rtn.put("appRel",configAppRelDao.getRel(configurationAppRelVO));
        rtn.put("envRel",configEnvRelDao.getRel(configurationEnvRelVO));
        return rtn;
    }
    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void update(Map<String,Object> map){
        String id = StringUtils.nullObj2String(map.get("id"));
        ConfigurationVO vo = new ConfigurationVO();
        try{
            vo.setId(Integer.valueOf(id));
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        vo.setKeyName(StringUtils.nullObj2String(map.get("name")));
        vo.setValue(StringUtils.nullObj2String(map.get("value")));
        vo.setType(StringUtils.nullObj2String(map.get("type")));
        vo.setDescription(StringUtils.nullObj2String(map.get("description")));
        configDao.updateById(vo);
        configEnvRelDao.updateRelStatusByConfigId(id,Constant.STATUS_UNUSEFUL);
        configAppRelDao.updateRelStatusByConfigId(id,Constant.STATUS_UNUSEFUL);
        List<String> envs = (ArrayList<String>)map.get("envArr");
        List<String> apps = (ArrayList<String>)map.get("appArr");
        for(String appId:apps){
            configAppRelDao.insert(vo.getId()+"",appId);
        }
        for(String envId:envs){
            configEnvRelDao.insert(vo.getId()+"",envId);
        }
    }

}
