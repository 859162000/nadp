package cn.com.netease.nadp.common.common;

/**
 * Created by bianlanzhou on 16/5/30.
 * Description 常量定义
 */
public final class Constants {
    public static final String ROOT = "/nadp";
    public static final String CONFIG_CENTER = ROOT + "/configCenter";
    public static final int ZK_CONNECT_TIME_OUT=500;    //zk超时时间
    public static final int ZK_SESSION_TIME_OUT=5000;    //zk超时时间ßß

    /**
     * applicationTyoe enum
     */
    public enum ApplicationType{
        /**
         * schedule type
         */
        schedule("schedule"),web("web");
        private String type;
        ApplicationType(String type) {
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
    }
}
