package cn.com.netease.nadp.monitor.resources.app;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.model.PaginationModel;
import cn.com.netease.nadp.monitor.model.ResultModel;
import cn.com.netease.nadp.monitor.service.app.AppService;
import cn.com.netease.nadp.monitor.utils.AppKeyGenUtils;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@NadpResource
@Path("/application")
public class AppResource {
    @Autowired
    private AppService service;
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/list")
    public ResultModel getConfig(Map<String,String> map) {
        ResultModel model = new ResultModel();
        try {
            PaginationModel paginationModel = new PaginationModel();
            paginationModel.setAaData(service.getData(null,null,map.get("iDisplayStart")==null?0:Integer.valueOf(map.get("iDisplayStart")), Constant.PAGINATION_MAX_COUNT));
            paginationModel.setiTotalDisplayRecords(service.getDataCount(null,null));
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
    public ResultModel insert(Map<String,Object> map){
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

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/genAppKey")
    public ResultModel genAppKey(Map<String,String> map){
        ResultModel model = new ResultModel();
        Map<String,String> info = new HashMap<String, String>(2);
        try {
            info.put("appKey", AppKeyGenUtils.getInstance().genAppKey());
            model.setInfo(info);
            model.setDefault(Constant.ResultCode.SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }


    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/listAll")
    public ResultModel listAll(Map<String,String> map){
        ResultModel model = new ResultModel();
        try {
            PaginationModel paginationModel = new PaginationModel();
            paginationModel.setiTotalRecords(Constant.PAGINATION_MAX_COUNT);
            paginationModel.setAaData(service.getAll(map.get("status")));
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


}
