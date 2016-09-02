package cn.com.netease.nadp.zookeeper;

/**
 * Created by bjbianlanzhou on 2016/8/22.
 */
public class ConfigurationNodeHandler implements ZkDataHandler {
    private String appPath ;
    private String envPath;
    private byte[] appData;
    private byte[] envData;
    public ConfigurationNodeHandler(String appPath,byte[] appData,String envPath,byte[] envData){
        this.appPath = appPath;
        this.envPath = envPath;
        this.envData = envData;
        this.appData = appData;
    };
    public void handleData(ZkSource source) {
        try {
            source.setData(appPath,appData);
            source.setData(envPath,envData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
