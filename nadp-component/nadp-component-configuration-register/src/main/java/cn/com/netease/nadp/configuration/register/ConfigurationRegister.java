package cn.com.netease.nadp.configuration.register;

import cn.com.netease.nadp.common.Constants;
import cn.com.netease.nadp.common.net.HttpUtils;
import cn.com.netease.nadp.zookeeper.ConfigurationNodeHander;
import cn.com.netease.nadp.zookeeper.ZkManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置中心
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class ConfigurationRegister implements ApplicationListener<ContextRefreshedEvent>{
    /**
     * 注册中心地址
     */
    private String address;
    /**
     * APPKEY
     */
    private String appKey;
    /**
     * 环境
     */
    private String env;
    /**
     * 环境名称
     */
    private String envName;
    /**
     * APP名称
     */
    private String appName;

    private ApplicationContext applicationContext;


    /**
     * 注册中心地址
     */
    private String zkAddress;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }




    /**
     * 容器初加载完成后调用
     * @param contextRefreshedEvent
     */
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.applicationContext = contextRefreshedEvent.getApplicationContext();
        getZkAddress();
        regist();
    }

    /**
     * 通过HTTP协议获取ZK配置
     */
    public void getZkAddress(){
        Map<String,String> map = new HashMap<String,String>(1);
        map.put("appKey",this.appKey);
        String jsonStr = new Gson().toJson(map);
        try {
            String message = HttpUtils.doPost(this.address+"/"+Constants.URL_CENTER,jsonStr);
            JsonObject obj = new JsonParser().parse(message).getAsJsonObject();
            String code = obj.get("code")==null?Constants.Result.FAIL.getCode():obj.get("code").getAsString();
            if(code.equals(Constants.Result.FAIL.getCode())){
                new RegistException(obj.get("message")==null?"":obj.get("message")+"").printStackTrace();
                System.exit(1);
            }
            if(obj.get("info")!=null){
                JsonObject info = obj.getAsJsonObject("info");
                if(info.get("zkAddress")!=null&&!"".equals(info.get("zkAddress"))){
                    this.zkAddress = info.get("zkAddress").getAsString();
                    this.env = info.get("env").getAsString();
                    this.appName = info.get("appName").getAsString();
                    this.envName = info.get("envName").getAsString();
                }else{
                    new RuntimeException("regist center error ! message :"+message).printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     */
    private void regist(){
        String appPath = "/"+ Constants.CONFIGURATION + "/"+ appKey;
        String envPath = appPath + "/" + env ;
        String path =envPath + "/" + Constants.DATA;
        try {
            ZkManager.getInstance().connect(this.zkAddress).cacheNode(path, new ConfigurationListener(appKey,applicationContext),new ConfigurationNodeHander(appPath,this.appName.getBytes("utf-8"),envPath,this.envName.getBytes("utf-8")));
        }catch (Exception ex){
            ex.printStackTrace();
            System.exit(0);
        }
    }

}
