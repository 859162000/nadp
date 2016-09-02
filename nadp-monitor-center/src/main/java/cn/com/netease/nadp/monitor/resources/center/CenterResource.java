package cn.com.netease.nadp.monitor.resources.center;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.model.ResultModel;
import cn.com.netease.nadp.monitor.service.center.CenterService;
import cn.com.netease.nadp.monitor.utils.NetUtils;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by bjbianlanzhou on 2016/8/22.
 */
@NadpResource
@Path("/center")
public class CenterResource {
    @Autowired
    private CenterService service;
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/registry")
    public ResultModel regist(Map<String,String> map, @Context HttpServletRequest request){
        String ip = NetUtils.getRemoteIp(request);
        String appKey = map.get("appKey");
        ResultModel model = new ResultModel();
        try {
            model.setInfo(service.getRegistCenter(ip,appKey));
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/getConfig")
    public ResultModel getConfig(Map<String,String> map,@Context HttpServletRequest request){
        String ip = NetUtils.getRemoteIp(request);
        String appKey = map.get("appKey");
        ResultModel model = new ResultModel();
        try {
            model.setInfo(service.getConfig(appKey,ip,Constant.ConfigurationType.MEMORY.getCode()));
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @GET
    @Path("/getPersistenceConfig")
    public void getPersistenceConfig(@Context HttpServletRequest request,@Context HttpServletResponse response){
        String ip = NetUtils.getRemoteIp(request);
        String appKey = request.getParameter("appKey");
        PrintWriter writer = null;
        if(appKey==null||"".equals(appKey)){
            try {
                writer =  response.getWriter();
                writer.write("appKey is null !");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            writer =  response.getWriter();
            List<Map<String,String>> configList = service.getConfig(appKey,ip,Constant.ConfigurationType.PERSISTENCE.getCode());
            for(Map<String,String> configMap : configList ){
                writer.write(configMap.get("key_name")+"="+configMap.get("value") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
             if(writer!=null){
                 writer.close();
             }
        }
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/notify")
    public ResultModel notify(Map<String,Object> map){
        String appId = StringUtils.nullObj2String(map.get("appId"));
        List<String> envs = (ArrayList<String>)map.get("envs");
        String appKey = StringUtils.nullObj2String(map.get("appKey"));
        ResultModel model = new ResultModel();
        try {
            for(String env : envs){
                service.notify(appId,env,appKey);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }
}
