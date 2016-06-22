package cn.com.netease.nadp.common.center;

import cn.com.netease.nadp.common.application.Application;
import cn.com.netease.nadp.common.common.Constants;
import cn.com.netease.nadp.common.utils.AESHelper;
import cn.com.netease.nadp.common.utils.NetUtils;
import cn.com.netease.nadp.common.utils.SerializeUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.Serializable;

/**
 * Created by bianlanzhou on 16/6/3.
 * Description
 */
public class RegistryCenter implements Serializable ,ApplicationListener<ContextRefreshedEvent> {
    private static final long serialVersionUID = 1L; //Serializable ID
    private String address;  //ZK地址
    private int port;   //ZK端口
    private CuratorFramework curator ;

    public final String getAddress() {
        return address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final int getPort() {
        return port;
    }

    public final void setPort(int port) {
        this.port = port;
    }

    /**
     * SPRING容器初始化完成后执行节点的注册 并初始化配置中心
     * @param contextRefreshedEvent
     */
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent()==null){
            System.out.println("application started!");
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            RegistryCenter registryCenter = (RegistryCenter)applicationContext.getBean("registry");
            Application application = (Application)applicationContext.getBean("application");
            try {
                registry(registryCenter, application);
                ConfigCenter.config(curator);
            }catch(Exception ex){
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * 注册节点信息
     * @param registryCenter
     * @param application
     * @throws Exception
     */
    private void registry(RegistryCenter registryCenter, Application application)throws Exception{
        String ip = NetUtils.getFirstRealIp();
        String uri = AESHelper.aesDecrypt(registryCenter.getAddress()) + ":" + registryCenter.getPort();
        String root = Constants.ROOT + "/" + application.getType() + "/" + application.getName() + "/" ;
        curator = CuratorFrameworkFactory.newClient(uri, Constants.ZK_SESSION_TIME_OUT,
                Constants.ZK_CONNECT_TIME_OUT,
                new RetryNTimes(5, 1000));
        curator.start();
        byte[] seriApp = SerializeUtils.serialize(application);
        CreateMode createMode ;
        if(Constants.ApplicationType.schedule.getType().equals(application.getType())){//如果是SCHEDULE则使用带有REQ的临时节点
            registrySchedule(curator,seriApp,root+ip,CreateMode.EPHEMERAL);
        }else if(Constants.ApplicationType.web.getType().equals(application.getType())){//如果是WEB则使用普通临时节点
            registryWeb(curator,seriApp,root+ip,CreateMode.EPHEMERAL);
        }else{
            new RuntimeException(" unknown application type ! ").printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 注册定时任务节点
     * @param curator
     * @param path
     * @param createMode
     */
    private void registrySchedule(CuratorFramework curator,byte[] seriApp,String path,CreateMode createMode)throws Exception{
        ZKConnectListener zkConnectListener = new ZKConnectListener(path,seriApp,createMode);
        curator.getConnectionStateListenable().addListener(zkConnectListener);
        curator.create().creatingParentsIfNeeded().withMode(createMode).forPath(path + "-", seriApp);
    }

    /**
     * 注册WEB节点
     * @param curator
     * @param path
     * @param createMode
     */
    private void registryWeb(CuratorFramework curator,byte[] seriApp,String path,CreateMode createMode)throws Exception{
        ZKConnectListener zkConnectListener = new ZKConnectListener(path,seriApp,createMode);
        curator.getConnectionStateListenable().addListener(zkConnectListener);
        curator.create().creatingParentsIfNeeded().withMode(createMode).forPath(path,seriApp);
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
            if (connectionState == ConnectionState.LOST||connectionState == ConnectionState.RECONNECTED) {
                while (true) {
                    try {
                        if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                            if(curatorFramework.checkExists().forPath(path)==null){
                                curatorFramework.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, data);
                                break;
                            }else{
                                break;
                            }
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

    /**
     * 获取节点子数
     * @param type
     * @param path
     * @return
     */
    public int getChildrenCount(String type,String path){
        try {
            return curator.getChildren().forPath(path).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 关闭链接
     */
    public void destory(){
        curator.close();
    }

    /**
     * 注册完成后获取配置数据
     */
    private void getConfig(){
        curator.getData().usingWatcher(new Watcher() {
            public void process(WatchedEvent event) {
                if(event.getType() == Event.EventType.NodeDataChanged){
                    try {
                        curator.getData().forPath(Constants.CONFIG_CENTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        });
    }
}
