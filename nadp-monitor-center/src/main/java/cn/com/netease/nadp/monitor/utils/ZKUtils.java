package cn.com.netease.nadp.monitor.utils;

import cn.com.netease.nadp.common.common.Constants;
import cn.com.netease.nadp.common.utils.AESHelper;
import cn.com.netease.nadp.common.utils.SerializeUtils;
import cn.com.netease.nadp.common.vo.ConfigVO;
import cn.com.netease.nadp.monitor.dao.ConfigDao;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * cn.com.netease.nadp.monitor.utils
 * Created by bjbianlanzhou on 2016/6/22.
 * Description
 */
public class ZKUtils implements InitializingBean{
    @Autowired
    private ConfigDao configDao;
    private static ZKUtils zkUtils = null;
    private String uri;
    private ZKUtils(String uri){
        this.uri = uri ;
    }

    public ZKUtils(String address,int port){
        try {
            if(uri == null){
                uri = AESHelper.aesDecrypt(address) + ":" + port;
                zkUtils = new ZKUtils(uri);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    /**
     * 获取容器
     * @return
     */
    public static ZKUtils getZkUtils(){
        return zkUtils;
    }

    /**
     * 更新节点数据
     * @param data
     * @param path
     * @throws Exception
     */
    public void updateNodeData(byte[] data,String path)throws Exception{
        CuratorFramework curator = null;
        try {
            curator = CuratorFrameworkFactory.newClient(uri, Constants.ZK_SESSION_TIME_OUT,
                    Constants.ZK_CONNECT_TIME_OUT,
                    new RetryNTimes(5, 1000));
            curator.start();
            curator.setData().forPath(path);
        }catch (Exception ex){
            throw ex;
        }finally {
            curator.close();
        }
    }

    /**
     * 初始化节点
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        CuratorFramework curator = null;
        try {
            curator = CuratorFrameworkFactory.newClient(uri, Constants.ZK_SESSION_TIME_OUT,
                    Constants.ZK_CONNECT_TIME_OUT,
                    new RetryNTimes(5, 1000));
            curator.start();
            if(curator.checkExists().forPath(Constants.CONFIG_CENTER) == null){
                curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constants.CONFIG_CENTER,SerializeUtils.serialize(configDao.select(null)));
            }else{
                curator.setData().forPath(Constants.CONFIG_CENTER,SerializeUtils.serialize(configDao.select(null)));
            }
        }catch (Exception ex){
            throw ex;
        }finally {
            curator.close();
        }
    }
}
