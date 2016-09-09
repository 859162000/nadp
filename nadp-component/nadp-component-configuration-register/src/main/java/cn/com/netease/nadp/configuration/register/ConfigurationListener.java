package cn.com.netease.nadp.configuration.register;

import cn.com.netease.nadp.common.Constants;
import cn.com.netease.nadp.common.net.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节点数据变化监听类
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class ConfigurationListener implements NodeCacheListener {
    /**
     * APPKEY
     */
    private String appKey;

    private ApplicationContext applicationContext;
    /**
     * 防止启动加载两次
     */
    private boolean startLock = false;

    /**
     * constructor
     * @param appKey
     */
    public ConfigurationListener(String appKey,ApplicationContext applicationContext) {
        this.appKey = appKey;
        this.applicationContext = applicationContext;
        process();
    }

    /**
     * override method 节点变化监听方法
     * @throws Exception
     */
    public void nodeChanged() throws Exception {
        process();
    }

    private void process(){
        try {
            Map<String,ConfigurationHandler> handlerMap = applicationContext.getBeansOfType(ConfigurationHandler.class);
            List<Map<String,String>> configuration = getConfigurationFromCenter();
            if(!configuration.isEmpty()&&startLock){
                Configuration.getInstance().loadConfig(configuration,handlerMap);
            }
            if(!startLock){
                startLock = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Map<String,String>> getConfigurationFromCenter(){
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        JsonArray configuration = null;
        ConfigurationRegister register = (ConfigurationRegister)applicationContext.getBean("register");
        String url = register.getAddress() + "/" + Constants.CENTER_MEMERY;
        Map<String,String> paramMap = new HashMap<String, String>(2);
        paramMap.put("appKey",appKey);
        Gson gson = new Gson();
        try {
            String message = HttpUtils.doPost(url,gson.toJson(paramMap));
            if(message!=null){
                JsonObject json = new JsonParser().parse(message).getAsJsonObject();
                if(json.get("code")!=null&&Constants.Result.SUCCESS.getCode().equals(json.get("code").getAsString())){
                    configuration = json.get("info").getAsJsonArray();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(configuration!=null){
            list = gson.fromJson(configuration, new TypeToken<List<Map<String,String>>>(){}.getType());
        }
        return list;
    }

}
