package cn.com.netease.nadp.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;

import java.util.UUID;

/**
 * Created by bjbianlanzhou on 2016/9/19.
 */
public class ConnectionListener implements ConnectionStateListener {
    private String path;
    public ConnectionListener(String path){
        this.path = path;
    }
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if(connectionState == ConnectionState.LOST){
            while(true){
                try {
                    if(curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()){
                        if(curatorFramework.checkExists().forPath(path)==null){
                            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, UUID.randomUUID().toString().getBytes("utf-8"));
                        }
                        break;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
