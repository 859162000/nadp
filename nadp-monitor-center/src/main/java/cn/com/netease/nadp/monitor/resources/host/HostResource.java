package cn.com.netease.nadp.monitor.resources.host;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.model.PaginationModel;
import cn.com.netease.nadp.monitor.model.ResultModel;
import cn.com.netease.nadp.monitor.service.env.EnvService;
import cn.com.netease.nadp.monitor.service.host.HostService;
import cn.com.netease.nadp.monitor.utils.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@NadpResource
@Path("/host")
public class HostResource {
    @Autowired
    private HostService service;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/list")
    public ResultModel list(Map<String,String> map){
        ResultModel model = new ResultModel();
        try {
            PaginationModel paginationModel = new PaginationModel();
            paginationModel.setiTotalRecords(Constant.PAGINATION_MAX_COUNT);
            paginationModel.setAaData(service.getData(map.get("host"),Constant.STATUS_USEFUL,map.get("iDisplayStart")==null?0:Integer.valueOf(map.get("iDisplayStart")),Constant.PAGINATION_MAX_COUNT));
            paginationModel.setiTotalDisplayRecords(service.getDataCount(map.get("host"),Constant.STATUS_USEFUL));
            paginationModel.setsEcho(map.get("sEcho"));
            Map<String,Object> data = new HashMap<String, Object>();
            model.setInfo(paginationModel);
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/insert")
    public ResultModel insert(Map<String,Object> map){
        ResultModel model = new ResultModel();
        try {
            //校验数据
            if(map.get("host")==null){
                model.setDefault(Constant.ResultCode.FAIL);
                model.setInfo("地址不存在!");
                return model;
            }
            String host = map.get("host")+"";
            String[] hostsArr = host.split("/");
            if(hostsArr.length>2||hostsArr.length<=0){
                model.setDefault(Constant.ResultCode.FAIL);
                model.setInfo("请填写正确的IP段!");
                return model;
            }
            if(hostsArr.length==1){
                if(!NetUtils.validateIp(hostsArr[0])){
                    model.setDefault(Constant.ResultCode.FAIL);
                    model.setInfo("请填写合法IP!");
                    return model;
                }
            }
            if(hostsArr.length==2){
                if(!NetUtils.validateIp(hostsArr[0])){
                    model.setDefault(Constant.ResultCode.FAIL);
                    model.setInfo("请填写合法IP!");
                    return model;
                }
                try {
                    int last = Integer.valueOf(hostsArr[1]);
                    if(last<=0||last>255){
                        model.setDefault(Constant.ResultCode.FAIL);
                        model.setInfo("请填写合法IP!");
                        return model;
                    }
                }catch (Exception ex){
                    model.setDefault(Constant.ResultCode.FAIL);
                    model.setInfo("请填写合法IP!");
                    return model;
                }
            }
            service.insert(map);
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/update/getRel")
    public ResultModel getRel(Map<String,String> map) {
        ResultModel model = new ResultModel();
        try {
            model.setInfo(service.getRel(map));
            model.setDefault(Constant.ResultCode.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/update")
    public ResultModel update(Map<String,Object> map) {
        ResultModel model = new ResultModel();
        try {
            service.update(map);
            model.setDefault(Constant.ResultCode.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @DELETE
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/delete")
    public ResultModel delete(Map<String,String> map) {
        ResultModel model = new ResultModel();
        try {
            service.delete(map);
            model.setDefault(Constant.ResultCode.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }
}
