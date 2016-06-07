package cn.com.netease.nadp.common.registryCenter;

import cn.com.netease.nadp.common.common.Constants;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;

import java.io.Serializable;

/**
 * Created by bianlanzhou on 16/6/3.
 * Description
 */
public class RegistryCenter implements Serializable {
    private static final long serialVersionUID = 1L; //Serializable ID
    private String address;  //ZK地址
    private int port;   //ZK端口

    public RegistryCenter(String address,int port){
        this.address = address;
        this.port = port;
    }

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

}
