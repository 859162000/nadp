package cn.com.netease.nadp.monitor.service.host;

import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.dao.*;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import cn.com.netease.nadp.monitor.vo.HostAppRelVO;
import cn.com.netease.nadp.monitor.vo.HostEnvAppRelVO;
import cn.com.netease.nadp.monitor.vo.HostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@Service
public class HostServiceImpl implements HostService {
    @Autowired
    private HostDao dao;
    @Autowired
    private HostAppRelDao hostAppRelDao;
    @Autowired
    private HostEnvAppRelDao hostEnvAppRelDao;
    @Autowired
    private EnvDao envDao;
    @Autowired
    private AppDao appDao;
    public List<HostVO> getData(String host, String status, int pageFrom, int PageCapacity) {
        return dao.select(host, status, pageFrom, PageCapacity);
    }
    public int getDataCount(String host, String status){
        return dao.selectDataCount(host, status);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void insert(Map<String, Object> map) {
        HostVO vo = new HostVO();
        String appId = null;
        vo.setHost(StringUtils.nullObj2String(map.get("host")));
        vo.setDescription(StringUtils.nullObj2String(map.get("description")));
        dao.insert(vo);
        if(map.get("app")!=null){
            appId = map.get("app")+"";
            HostAppRelVO hostAppRelVO ;
            hostAppRelVO = new HostAppRelVO();
            hostAppRelVO.setAppId(appId);
            hostAppRelVO.setHostId(vo.getId()+"");
            hostAppRelDao.insert(hostAppRelVO);
        }
        if(map.get("env")!=null){
            List<String> envArr = (ArrayList<String>) map.get("env");
            HostEnvAppRelVO hostEnvAppRelVO = new HostEnvAppRelVO();
            for(String env : envArr){
                hostEnvAppRelVO.setEnvId(env);
                hostEnvAppRelVO.setAppId(appId);
                hostEnvAppRelVO.setHostId(vo.getId()+"");
                hostEnvAppRelDao.insert(hostEnvAppRelVO);
            }
        }
    }

    public Map<String,Object> getRel(Map<String,String> map){
        Map<String,Object> rtn = new HashMap<String,Object>();
        rtn.put("envList",envDao.selectAll(Constant.STATUS_USEFUL));
        rtn.put("appList",appDao.selectAll(Constant.STATUS_USEFUL));
        if(null!=map.get("id")&&!"".equals(map.get("id"))){
            rtn.put("appRelList",hostAppRelDao.select(map.get("id"),null,Constant.STATUS_USEFUL));
            rtn.put("envRelList",hostEnvAppRelDao.select(null,map.get("id"),null,Constant.STATUS_USEFUL));
        }else{
            rtn.put("appRelList",new ArrayList());
            rtn.put("envRelList",new ArrayList());
        }
       return rtn;
    }

    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void update(Map<String,Object> map){
        int id ;
        try{
            id = Integer.valueOf(StringUtils.nullObj2String(map.get("id")));
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        HostVO hostVO = new HostVO();
        hostVO.setId(id);
        hostVO.setHost(StringUtils.nullObj2String(map.get("host")));
        hostVO.setDescription(StringUtils.nullObj2String(map.get("description")));
        dao.updateById(hostVO);
        HostAppRelVO hostAppRelVo = new HostAppRelVO();
        hostAppRelVo.setHostId(id + "");
        hostAppRelVo.setStatus(Constant.STATUS_UNUSEFUL);
        hostAppRelDao.updateByHostId(hostAppRelVo);
        HostEnvAppRelVO hostEnvAppRelVo = new HostEnvAppRelVO();
        hostEnvAppRelVo.setHostId(id+"");
        hostEnvAppRelVo.setStatus(Constant.STATUS_UNUSEFUL);
        hostEnvAppRelDao.updateByHostId(hostEnvAppRelVo);
        String appId = null;
        if(map.get("app")!=null){
            appId = map.get("app")+"";
            HostAppRelVO hostAppRelVO ;
            hostAppRelVO = new HostAppRelVO();
            hostAppRelVO.setAppId(appId);
            hostAppRelVO.setHostId(id+"");
            hostAppRelDao.insert(hostAppRelVO);
        }
        if(map.get("env")!=null){
            List<String> envArr = (ArrayList<String>) map.get("env");
            HostEnvAppRelVO hostEnvAppRelVO = new HostEnvAppRelVO();
            for(String env : envArr){
                hostEnvAppRelVO.setEnvId(env);
                hostEnvAppRelVO.setAppId(appId);
                hostEnvAppRelVO.setHostId(id+"");
                hostEnvAppRelDao.insert(hostEnvAppRelVO);
            }
        }
    }

    public void delete(Map<String,String> map){
        int id ;
        try{
            id = Integer.valueOf(map.get("id"));
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        HostVO hostVO = new HostVO();
        hostVO.setId(id);
        hostVO.setStatus(Constant.STATUS_UNUSEFUL);
        dao.updateById(hostVO);
    }
}
