package cn.com.netease.nadp.zookeeper;

import cn.com.netease.nadp.zookeeper.api.IZkSource;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper source
 * Created by bjbianlanzhou on 2016/7/29.
 * Description
 */
final class ZkSource implements IZkSource{
    /**
     * curator连接类
     */
    private static CuratorFramework curator  = null;


    /**
     * zookeeper连接信息
     */
    private ZkConnectInfo zkConnectInfo = new ZkConnectInfo();

    /**
     * 构造函数
     * @param host
     * @param port
     */
    protected ZkSource(String host,int port){
        this(host + ":" + port);
    }

    /**
     * 构造函数
     * @param connectString
     */
    protected ZkSource(String connectString){
        zkConnectInfo.setConnectString(connectString);
        zkConnectInfo.setZkSessionTimeOut(0);
        zkConnectInfo.setZkConnectTimeOut(0);
        connect();
    }

    /**
     * 构造函数
     * @param host
     * @param port
     * @param zkConnectTimeOut
     * @param zkSessionTimeOut
     */
    protected ZkSource(String host,int port,int zkConnectTimeOut,int zkSessionTimeOut){
        this(host, port);
        zkConnectInfo.setZkConnectTimeOut(zkConnectTimeOut);
        zkConnectInfo.setZkSessionTimeOut(zkSessionTimeOut);
    }

    /**
     * 连接zookeeper
     */
    protected void connect(){
        if(null==curator){
            curator = CuratorFrameworkFactory.newClient(zkConnectInfo.getConnectString(),zkConnectInfo.getZkSessionTimeOut(),
                    zkConnectInfo.getZkConnectTimeOut(),
                    new RetryNTimes(5,1000));
        }
        curator.start();
        try {
            boolean connected = curator.blockUntilConnected(10,TimeUnit.SECONDS);
            if(!connected){
                new RuntimeException("zookeeper can not connected!").printStackTrace();
                System.exit(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监控节点状态的改变，并提供listener进行处理
     * @param path
     * @param listener
     * @throws Exception
     */
    public void cacheNode(String path,NodeCacheListener listener) throws Exception {
        NodeCache nodeCache = null;
        if(curator.checkExists().forPath(path)==null){
            curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            curator.setData().forPath(path, UUID.randomUUID().toString().getBytes("utf-8"));
        }
        nodeCache = new NodeCache(curator,path);
        nodeCache.start();
        nodeCache.getListenable().addListener(listener);
    }

    /**
     * 放置数据
     * @param path
     * @param bytes
     */
    public void setData(String path,byte[] bytes)throws Exception{
        if(curator.checkExists().forPath(path)!=null) {
            curator.setData().forPath(path, bytes);
        }
    }

    /**
     * 获取节点数据
     * @param path
     */
    public byte[] getData(String path) throws Exception {
         return curator.getData().forPath(path);
    }

    public boolean checkNodeExists(String path) throws Exception {
        return curator.checkExists().forPath(path)!=null;
    }
    /**
     * zookeeper连接信息
     */
    private class ZkConnectInfo{
        /**
         * 默认SESSION超时时间
         */
        private static final int DEFAULT_ZK_SESSION_TIME_OUT = 500;
        /**
         * 默认连接超时时间
         */
        private static final int DEFAULT_ZK_CONNECT_TIME_OUT = 500;
        /**
         * 连接字符串
         */
        private String connectString;
        /**
         * 连接超时时间
         */
        private int zkConnectTimeOut;
        /**
         * 默认超时时间
         */
        private int zkSessionTimeOut;

        public String getConnectString() {
            return connectString;
        }

        public void setConnectString(String connectString) {
            this.connectString = connectString;
        }

        public int getZkConnectTimeOut() {
            return zkConnectTimeOut;
        }

        public void setZkConnectTimeOut(int zkConnectTimeOut) {
            if(zkConnectTimeOut<=0){
                this.zkConnectTimeOut = DEFAULT_ZK_CONNECT_TIME_OUT;
            }else {
                this.zkConnectTimeOut = zkConnectTimeOut;
            }
        }

        public int getZkSessionTimeOut() {
            return zkSessionTimeOut;
        }

        public void setZkSessionTimeOut(int zkSessionTimeOut) {
            if(zkSessionTimeOut<=0){
                this.zkSessionTimeOut = DEFAULT_ZK_SESSION_TIME_OUT;
            }else {
                this.zkSessionTimeOut = zkSessionTimeOut;
            }
        }
    }




}
