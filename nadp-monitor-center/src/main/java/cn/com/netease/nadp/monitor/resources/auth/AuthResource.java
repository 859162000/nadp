package cn.com.netease.nadp.monitor.resources.auth;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.model.PaginationModel;
import cn.com.netease.nadp.monitor.model.ResultModel;
import cn.com.netease.nadp.monitor.service.auth.AuthService;
import cn.com.netease.nadp.monitor.utils.MD5Utils;
import cn.com.netease.nadp.monitor.vo.AuthVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/9/8.
 */
@NadpResource
@Path("/auth")
public class AuthResource {

    @Autowired
    private AuthService service;

    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Path("/login")
    public ResultModel getConfig(Map<String,String> map, @Context HttpServletRequest request) {
        ResultModel model = new ResultModel();
        try {
            model.setDefault(Constant.ResultCode.SUCCESS);
            AuthVO vo = new AuthVO();
            vo.setUserName(map.get("username"));
            vo.setPassword(map.get("password"));
            boolean result = service.auth(vo);
            if(result){
                vo.setPassword("");
                request.getSession().setMaxInactiveInterval(1800);
                request.getSession().setAttribute("auth",vo);
                model.setInfo("success");
            }else{
                model.setInfo("fail");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            model.setDefault(Constant.ResultCode.FAIL);
        }
        return model;
    }

    public static void main(String[] args) {
        try {
            System.out.println((double)7/60);
            System.out.println(MD5Utils.Bit16("1234567"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
