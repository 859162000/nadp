package cn.com.netease.nadp.monitor.service.host;

import cn.com.netease.nadp.monitor.dao.HostAppRelDao;
import cn.com.netease.nadp.monitor.dao.HostDao;
import cn.com.netease.nadp.monitor.dao.HostEnvAppRelDao;
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

}
