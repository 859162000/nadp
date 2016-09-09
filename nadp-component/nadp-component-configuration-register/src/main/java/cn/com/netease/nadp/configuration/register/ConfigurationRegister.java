package cn.com.netease.nadp.configuration.register;

import cn.com.netease.nadp.common.Constants;
import cn.com.netease.nadp.common.net.HttpUtils;
import cn.com.netease.nadp.common.utils.StringUtils;
import cn.com.netease.nadp.zookeeper.ConfigurationNodeHandler;
import cn.com.netease.nadp.zookeeper.ZkManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 配置中心
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class ConfigurationRegister implements ApplicationContextAware{
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

    /**
     * 注册中心地址
     */
    private String zkAddress;
    /**
     * 类型 center\local
     */
    private String type;

    /**
     * 配置文件地址
     */
    private String location;

    private ApplicationContext applicationContext;

    public Map<String,String> persistence = new HashMap<String,String>();

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        processMemeryConfiguration();
    }
    public void process(){
        processPersistence();
        processMemeryConfiguration();
    }
    /**
     * 处理持久化配置
     */
    private void processPersistence()  {
        if(Constants.ConfigurationRegisterType.LOCAL.getType().equals(type)){
            processLocalPersistence();
        }else{  //默认CENTER
            processCenterPersistence();
        }
    }

    /**
     * 获取本机的配置
     */
    private void processLocalPersistence()  {
        InputStream is = this.getClass().getResourceAsStream(location);
        Properties p = new Properties();
        try {
            p.load(is);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for(Map.Entry entry : p.entrySet()){
            persistence.put(StringUtils.obj2String(entry.getKey()),StringUtils.obj2String(entry.getValue()));
        }
    }

    /**
     * 获取配置中心的配置
     */
    private void processCenterPersistence()  {
        JsonObject json = new JsonObject();
        json.addProperty("appKey",appKey);
        String message = null;
        try {
            message = HttpUtils.doPost(this.address+"/"+Constants.CENTER_PERSISTENCE,json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject obj = new JsonParser().parse(message).getAsJsonObject();
        if(Constants.Result.FAIL.getCode().equals(obj.get("code").getAsString())){
            //log here
            throw new RuntimeException("center refused !");
        }
        JsonArray persistenceArr = obj.getAsJsonArray("info");
        List<Map<String,String>> persistenceList = (new Gson()).fromJson(persistenceArr, new TypeToken<List<Map<String,String>>>(){}.getType());
        if(persistenceList == null || persistenceList.size()<=0){
            return ;
        }
        for(Map<String,String> configurationMap : persistenceList){
            persistence.put(configurationMap.get("key_name"),configurationMap.get("value"));
        }
    }



    /**
     * 处理内存配置
     */
    public void processMemeryConfiguration(){
        if(Constants.ConfigurationRegisterType.LOCAL.getType().equals(type)){
            processLocalMemeryConfiguration();
        }else{
            processCenterMemeryConfiguration();
        }
    }

    /**
     * 处理本地配置
     */
    public void processLocalMemeryConfiguration(){
        Map<String,ConfigurationHandler> handlerMap = applicationContext.getBeansOfType(ConfigurationHandler.class);
        InputStream is = this.getClass().getResourceAsStream(location);
        Properties p = new Properties();
        try {
            p.load(is);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Configuration.getInstance().loadConfig(p,handlerMap);
    }

    /**
     * 处理配置中心内存配置
     */
    public void processCenterMemeryConfiguration(){
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
            System.out.println(this.address+"/"+Constants.URL_CENTER+"&"+jsonStr);
            String message = HttpUtils.doPost(this.address+"/"+Constants.URL_CENTER,jsonStr);
            System.out.println("message : " + message);
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
            ZkManager.getInstance().connect(this.zkAddress).cacheNode(path, new ConfigurationListener(appKey,applicationContext),new ConfigurationNodeHandler(appPath,this.appName.getBytes("utf-8"),envPath,this.envName.getBytes("utf-8")));
        }catch (Exception ex){
            ex.printStackTrace();
            System.exit(0);
        }
    }


}