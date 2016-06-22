package cn.com.netease.nadp.monitor.resources.config;

import cn.com.netease.nadp.common.vo.ConfigVO;
import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.services.config.IConfigService;
import cn.com.netease.nadp.monitor.vo.RespVO;
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
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("/list")
    public RespVO getConfig(Map<String,String> map) {
        String key = map.get("key");
        List<ConfigVO> list;
        RespVO vo = new RespVO();
        Map<String,Object> data = new HashMap<String, Object>(1);
        try{
            list = configService.select(key);
            data.put("data",list);
            vo.setDefault(Constant.ResultCode.SUCCESS);
            vo.setData(data);
        }catch(Exception ex){
            ex.printStackTrace();
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("/update/{id}/{value}")
    public RespVO updateConfig(@PathParam("id") int id,@PathParam("value") String value){
        RespVO vo = new RespVO();
        try{
            vo.setDefault(Constant.ResultCode.SUCCESS);
            configService.update(id, value);
        }catch (Exception ex){
            ex.printStackTrace();
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }

    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Path("/insert")
    public RespVO insertConfig(Map<String,String> map){
        RespVO vo = new RespVO();
        String key = map.get("key");
        String value = map.get("value");
        String description = map.get("description");
        try{
            vo.setDefault(Constant.ResultCode.SUCCESS);
            configService.insert(key,value,description);
        }catch (Exception ex){
            ex.printStackTrace();
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }
    @GET
    @Path("/notifyCenter")
    public RespVO notifyCenter(){
        RespVO vo = new RespVO();
        List<ConfigVO> list;
        try{
            list = configService.select(null);
            configService.publicConfig(list);
            vo.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            vo.setDefault(Constant.ResultCode.FAIL);
        }
        return vo;
    }
}
