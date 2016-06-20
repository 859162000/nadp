package cn.com.netease.nadp.monitor.resources.config;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.services.config.IConfigService;
import cn.com.netease.nadp.monitor.vo.ConfigVO;
import cn.com.netease.nadp.monitor.vo.RespVO;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cn.com.netease.nadp.web.monitor.resources.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@NadpResource
@Path("/config")
public class ConfigResource {

    @Autowired
    private IConfigService configService;
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("/list/{key}")
    public RespVO getConfig(@PathParam("key") String key) {
        List<ConfigVO> list = null;
        RespVO vo = new RespVO();
        Map<String,Object> data = new HashMap<String, Object>(1);
        try{
            list = configService.select(key);
            data.put("data",list);
            vo.setDefault(Constant.ResultCode.SUCCESS);
            vo.setData(data);
        }catch(Exception ex){
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("/update/{key}/{value}")
    public RespVO updateConfig(@PathParam("key") String key,@PathParam("value") String value){
        RespVO vo = new RespVO();
        try{
            vo.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("/insert")
    public RespVO insertConfig(JsonObject json){
        RespVO vo = new RespVO();
        try{
            vo.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }






}
