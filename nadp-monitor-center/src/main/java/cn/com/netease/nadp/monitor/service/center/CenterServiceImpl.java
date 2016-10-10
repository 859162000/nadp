package cn.com.netease.nadp.monitor.service.center;

import cn.com.netease.nadp.common.Constants;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.dao.*;
import cn.com.netease.nadp.monitor.utils.NetUtils;
import cn.com.netease.nadp.monitor.utils.SpringUtils;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import cn.com.netease.nadp.monitor.vo.*;
import cn.com.netease.nadp.zookeeper.ZkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by bjbianlanzhou on 2016/8/22.
 */
@Service
public class CenterServiceImpl implements CenterService {
    @Autowired
    private HostDao hostDao;
    @Autowired
    private AppDao appDao;
    @Autowired
    private HostEnvAppRelDao hostEnvAppRelDao;
    @Autowired
    private HostAppRelDao hostAppRelDao;
    @Autowired
    private EnvDao envDao;
    @Autowired
    private ConfigDao configDao;
    public Map<String,Object> getRegistCenter(String ip, String appKey){
        Map<String,Object> rc = new HashMap<String, Object>();
        String appName = "";
        String zkAddress = "";
        String envId = "";
        String envName = "";
        boolean hostAccess = false;
        boolean keyAccess = false;
        boolean appAccess = false;
        boolean envAccess = false;
        //先通过IP及KEY查看是否有权限
        List<HostVO> hostList = hostDao.select(ip, Constants.Status.TRUE.getCode(),0,0);
        if(hostList!=null&&!hostList.isEmpty()){
            hostAccess = true;
        }
        if(hostAccess){
            int hostId = hostList.get(0).getId();
            List<AppVO> appList = appDao.select(null,appKey,Constants.Status.TRUE.getCode(),0,0);
            if(appList!=null&&!appList.isEmpty()){
                appName = appList.get(0).getName();
                keyAccess = true;
            }
            if(keyAccess){
                int appId = appList.get(0).getId();
                List<HostAppRelVO> hostAppRelList = hostAppRelDao.select(hostId+"",appId+"",Constant.STATUS_USEFUL);
                if(hostAppRelList != null && !hostAppRelList.isEmpty()){
                    appAccess = true;
                }
                if(keyAccess){
                    List<HostEnvAppRelVO> hostEnvAppRelList = hostEnvAppRelDao.select(appId+"",hostId+"",null,Constants.Status.TRUE.getCode());
                    if(hostEnvAppRelList !=null && !hostEnvAppRelList.isEmpty()) {
                        envId = hostEnvAppRelList.get(0).getEnvId();
                        List<EnvVO> envList = envDao.select(envId,null,null,0,0);
                        if(envList != null&&!envList.isEmpty()){
                            envAccess = true;
                            zkAddress = envList.get(0).getZkAddress();
                            envName = envList.get(0).getName();
                        }
                    }
                }
            }
        }
        rc.put("hostAccess",hostAccess);
        rc.put("keyAccess",keyAccess);
        rc.put("appAccess",appAccess);
        rc.put("envAccess",envAccess);
        rc.put("zkAddress",zkAddress);
        rc.put("ip",ip);
        rc.put("env",envId);
        rc.put("envName",envName);
        rc.put("appKey",appKey);
        rc.put("appName",appName);
        return rc;
    }

    public List<Map<String,String>> getConfig(String appKey,String ip,String type){
        List<AppVO> appList = appDao.select(null,appKey,Constants.Status.TRUE.getCode(),0,0);
        if(appList == null || appList.size()<=0){
            return null;
        }
        List<HostVO> hostList = hostDao.select(ip,Constants.Status.TRUE.getCode(),0,0);
        if(hostList == null || hostList.size()<=0){
            boolean flag = false;
            hostList = hostDao.select(null,Constants.Status.TRUE.getCode(),0,0);
            for(HostVO vo:hostList){
                if(vo.getHost().indexOf("/")<0){
                    continue;
                }else{
                    if(NetUtils.ipIsInRange(ip,vo.getHost())){
                        flag = true;
                        break;
                    }
                }
            }
            if(!flag){
                return null;
            }
        }
        String hostId = hostList.get(0).getId()+"";
        String appId = appList.get(0).getId()+"";
        List<HostEnvAppRelVO> hostEnvAppRelList = hostEnvAppRelDao.select(appId,hostId,null,Constants.Status.TRUE.getCode());
        if(hostList == null || hostList.size()<=0){
            return null;
        }
        String envId = hostEnvAppRelList.get(0).getEnvId();
        List<Map<String,String>> configurationList = configDao.getConfiguration(appId,envId,type);
        return configurationList;
    }

    public void notify(String appId, String envId,String appKey) {
        String data ;
        String [] dataArr ;
        String temp ;
        Properties properties = (Properties)SpringUtils.getBean("app");
        String zkAddress = StringUtils.nullObj2String(properties.get("zk_address"));
        String path = Constant.PATH_ROOT + "/" + appKey + "/" + envId ;
        try {
            if(!ZkManager.getInstance().checkConnected()){
                ZkManager.getInstance().connect(zkAddress);
            }
            List<String> nodes = ZkManager.getInstance().connect(zkAddress).getChildren(path);
            for(String node:nodes){
                ZkManager.getInstance().connect(zkAddress).setData(path+"/"+node, (UUID.randomUUID().toString()).getBytes("utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
