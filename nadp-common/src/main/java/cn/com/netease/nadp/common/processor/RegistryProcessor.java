package cn.com.netease.nadp.common.processor;

import cn.com.netease.nadp.common.application.Application;
import cn.com.netease.nadp.common.common.Constants;
import cn.com.netease.nadp.common.registryCenter.RegistryCenter;
import cn.com.netease.nadp.common.utils.NetUtils;
import cn.com.netease.nadp.common.utils.SerializeUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by bianlanzhou on 16/6/6.
 * Description
 */
public final class RegistryProcessor {
    /**
     * 在ZOOKEEPER注册服务相关信息
     * @param registryCenter
     * @param application
     */
    protected static void doRegistry(RegistryCenter registryCenter,Application application){
        String type = application.getType();
        try{
            if(null==type||"".equals(type)){
                new RuntimeException("nadp:application type error!").printStackTrace();
                System.exit(1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }



    private void createNodes(RegistryCenter registryCenter, Application application)throws Exception{
        String ip = NetUtils.getFirstRealIp();
        String uri = registryCenter.getAddress() + ":" + registryCenter.getPort();
        String path = Constants.ROOT + "/" + application.getType() + "/" + application.getName() + "/" + ip;
        CuratorFramework curator = CuratorFrameworkFactory.newClient(uri,Constants.ZK_SESSION_TIME_OUT,
                                        Constants.ZK_CONNECT_TIME_OUT,
                                            new RetryNTimes(5, 1000));
        curator.start();
        byte[] seriApp = SerializeUtils.serialize(application);
        CreateMode createMode ;
        if(Constants.ApplicationType.schedule.getType().equals(application.getType())){//如果是SCHEDULE则使用带有REQ的临时节点
            createMode = CreateMode.EPHEMERAL_SEQUENTIAL.EPHEMERAL;
            ZKConnectListener zkConnectListener = new ZKConnectListener(path,seriApp,createMode);
            curator.getConnectionStateListenable().addListener(zkConnectListener);
            curator.create().creatingParentsIfNeeded().withMode(createMode).forPath(path + "-", seriApp);
        }else if(Constants.ApplicationType.web.getType().equals(application.getType())){//如果是WEB则使用普通临时节点
            createMode = CreateMode.EPHEMERAL;
            ZKConnectListener zkConnectListener = new ZKConnectListener(path,seriApp,createMode);
            curator.getConnectionStateListenable().addListener(zkConnectListener);
            curator.create().creatingParentsIfNeeded().withMode(createMode).forPath(path,seriApp);
        }else{
            new RuntimeException(" unknown application type ! ");
            System.exit(1);
        }
    }

    /**
     * 防止网络闪断造成临时节点的丢失
     */
    private class ZKConnectListener implements ConnectionStateListener {
        private String path;
        private byte[] data;
        private CreateMode createMode;
        public ZKConnectListener(String path, byte[] data,CreateMode createMode) {
            this.path = path;
            this.data = data;
            this.createMode = createMode;
        }
        public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
            if (connectionState == ConnectionState.LOST) {
                while (true) {
                    try {
                        if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                            curatorFramework.create().creatingParentsIfNeeded().withMode(createMode)
                                    .forPath(path, data);
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
