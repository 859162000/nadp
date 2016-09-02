package cn.com.netease.nadp.zookeeper;

import cn.com.netease.nadp.zookeeper.exception.ZkReConnectedException;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * zookeeper manager
 * Created by bjbianlanzhou on 2016/7/29.
 * Description
 */
public class ZkManager {
    /**
     * zookeeper source
     */
    private static ZkSource zkSource = null;

    /**
     * constructer
     */
    private ZkManager(){

    }

    /**
     * static singleton
     */
    private static class ZkManagerBuilder {
        private static ZkManager zkManager = new ZkManager();
    }
    /**
     * static singleton
     */
     public static ZkManager getInstance() {
         return ZkManagerBuilder.zkManager;
     }

    /**
     * 此方法只供初次连接时调用，如果再次调用会抛出重新连接的异常
     * @throws ZkReConnectedException
     */
    public ZkManager connect(String host,int port)throws ZkReConnectedException{
        if(zkSource==null){
            zkSource = new ZkSource(host,port);
        }else{
            throw new ZkReConnectedException();
        }
        return this;
    }
    /**
     * 此方法只供初次连接时调用，如果再次调用则会返回最开始的
     * @throws ZkReConnectedException
     */
    public ZkManager connect(String address)throws ZkReConnectedException{
        if(zkSource==null){
            zkSource = new ZkSource(address);
        }
        return this;
    }

    /**
     * 此方法只供初次连接时调用，如果再次调用会抛出重新连接的异常
     * @throws ZkReConnectedException
     */
    public ZkManager connect(String host,int port,int zkConnectTimeOut,int zkSessionTimeOut)throws ZkReConnectedException{
        if(zkSource==null){
            zkSource = new ZkSource(host,port,zkConnectTimeOut,zkSessionTimeOut);
        }else{
            throw new ZkReConnectedException();
        }
        return this;
    }

    /**
     * 检查是否已经连接
     * @return
     */
    public boolean checkConnected(){
        return this.zkSource != null;
    }

    /**
     * cache node
     * @param path
     * @param listener
     * @throws Exception
     */
    public void cacheNode(String path, NodeCacheListener listener,ZkDataHandler handler) throws Exception {
        zkSource.cacheNode(path,listener);
        if(handler!=null){
            handler.handleData(zkSource);
        }
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     * @throws Exception
     */
    public byte[] getData(String path) throws Exception {
        return zkSource.getData(path);
    }

    public boolean checkNodeExists(String path) throws Exception {
        return zkSource.checkNodeExists(path);
    }

    public void setData(String path,byte[] bytes) throws Exception {
        zkSource.setData(path,bytes);
    }


}
