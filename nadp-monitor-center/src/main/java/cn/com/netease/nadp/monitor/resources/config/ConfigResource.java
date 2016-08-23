package cn.com.netease.nadp.monitor.resources.config;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.model.PaginationModel;
import cn.com.netease.nadp.monitor.model.ResultModel;
import cn.com.netease.nadp.monitor.service.config.ConfigService;
import cn.com.netease.nadp.monitor.utils.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * cn.com.netease.nadp.web.monitor.resources.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@NadpResource
@Path("/configuration")
public class ConfigResource {

    @Autowired
    private ConfigService service;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/list")
    public ResultModel getConfig(Map<String,String> map) {
        ResultModel model = new ResultModel();
        try {
            PaginationModel paginationModel = new PaginationModel();
            paginationModel.setAaData(service.getData(null,null,null,map.get("iDisplayStart")==null?0:Integer.valueOf(map.get("iDisplayStart")),Constant.PAGINATION_MAX_COUNT));
            paginationModel.setiTotalDisplayRecords(service.getDataCount(null,null,null));
            paginationModel.setiTotalRecords(Constant.PAGINATION_MAX_COUNT);
            paginationModel.setsEcho(map.get("sEcho"));
            Map<String,Object> data = new HashMap<String, Object>();
            model.setInfo(paginationModel);
            model.setResultMessage(Constant.ResultMessage.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/insert")
    public ResultModel insert(Map<String,Object> map) {
        ResultModel model = new ResultModel();
        try {
            service.insert(map);
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @DELETE
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/delete")
    public ResultModel delete(Map<String,String> map){
        ResultModel model = new ResultModel();
        Map<String,Integer> refMap = new HashMap<String, Integer>(1);
        try {
            service.updateById(map.get("id")==null?"":map.get("id"),null,null,Constant.CONFIGURATION_STATUS);
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }




}
