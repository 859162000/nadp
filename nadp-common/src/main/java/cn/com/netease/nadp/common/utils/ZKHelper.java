package cn.com.netease.nadp.common.utils;

import cn.com.netease.nadp.common.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.List;

/**
 * Created by bianlanzhou on 16/6/14.
 * Description
 */
public class ZKHelper {
    private static ZKHelper zkHelper = new ZKHelper();
    private ZKHelper(){};
    public ZKHelper getInstance(){
        if(zkHelper==null){
            synchronized (this){
                zkHelper = new ZKHelper();
            }
        }
        return zkHelper;
    }

    /**
     * ZK节点获取数据
     * @param url
     * @param port
     * @return
     */
    public byte[] getData(String url,int port,List<ACL> list){
        byte[] data = null;
        String uri = null;
        try {
            uri = AESHelper.aesDecrypt(url) + ":" + port;
        } catch (Exception e) {
            e.printStackTrace();
        }
        CuratorFramework curator = CuratorFrameworkFactory.newClient(uri, Constants.ZK_SESSION_TIME_OUT,
                        Constants.ZK_CONNECT_TIME_OUT,
                        new RetryNTimes(5, 1000));
        try {
            curator.setACL().withACL(list);
            data = curator.getData().forPath("/nadp/monitor/all");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
