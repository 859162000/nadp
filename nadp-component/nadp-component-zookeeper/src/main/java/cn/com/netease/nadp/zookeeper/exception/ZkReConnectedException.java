package cn.com.netease.nadp.zookeeper.exception;

/**
 * Created by bjbianlanzhou on 2016/8/1.
 */
public class ZkReConnectedException extends RuntimeException{
    private static String RECONNECT_EXCEPTION = "zk has bean connected .";
    public ZkReConnectedException(){
        super(RECONNECT_EXCEPTION);
    }
}
