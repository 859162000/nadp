package cn.com.netease.nadp.monitor.resources.config;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.model.PaginationModel;
import cn.com.netease.nadp.monitor.model.ResultModel;
import cn.com.netease.nadp.monitor.service.app.AppService;
import cn.com.netease.nadp.monitor.service.config.ConfigService;
import cn.com.netease.nadp.monitor.service.env.EnvService;
import cn.com.netease.nadp.monitor.utils.NetUtils;
import cn.com.netease.nadp.monitor.vo.AppVO;
import cn.com.netease.nadp.monitor.vo.ConfigurationVO;
import org.springframework.beans.factory.annotation.Autowired;
import sun.util.resources.CalendarData_ar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private AppService appService;
    @Autowired
    private EnvService envService;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/list")
    public ResultModel getConfig(Map<String,String> map) {
        ResultModel model = new ResultModel();
        try {
            PaginationModel paginationModel = new PaginationModel();
            paginationModel.setAaData(service.getData(null,map.get("name"),null,Constant.STATUS_USEFUL,map.get("iDisplayStart")==null?0:Integer.valueOf(map.get("iDisplayStart")),Constant.PAGINATION_MAX_COUNT));
            paginationModel.setiTotalDisplayRecords(service.getDataCount(null,null,Constant.STATUS_USEFUL));
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
            service.updateById(map.get("id")==null?"":map.get("id"));
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/update/list")
    public ResultModel getDataForUpdate(Map<String,String> map) {
        ResultModel model = new ResultModel();
        String id = map.get("id");
        Map<String,Object> infoMap = new HashMap<String, Object>();
        try {
            model.setDefault(Constant.ResultCode.SUCCESS);
            List<ConfigurationVO> list = service.getData(id,null,null,null,0,0);
            if(list!=null&&list.size()>0){
                infoMap.put("vo",list.get(0));
            }else{
                return model;
            }
            infoMap.put("appList",appService.getAll(Constant.STATUS_USEFUL));
            infoMap.put("envList",envService.getAll(Constant.STATUS_USEFUL));
            infoMap.putAll(service.getRel(id));
            model.setInfo(infoMap);
        }catch (Exception ex){
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
        Map<String,Object> infoMap = new HashMap<String, Object>();
        try {
           //先舍弃  过去的数据  将过去的数据状态改成0   然后插入新的数据，达到逻辑删除的效果。
           service.update(map);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }


    public static void main(String[] args) {
        Calendar ca = Calendar.getInstance();
        ca.set(ca.YEAR,2016);
        ca.set(ca.MONTH,8);
        ca.set(ca.DAY_OF_MONTH,0);
        ca.set(ca.HOUR_OF_DAY,0);
        ca.set(ca.MINUTE, 0);
        ca.set(ca.SECOND, 0);
        long t = ca.getTime().getTime();
        System.out.println(t);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(t)));
    }

}
