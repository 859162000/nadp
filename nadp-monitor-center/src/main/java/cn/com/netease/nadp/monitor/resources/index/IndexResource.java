package cn.com.netease.nadp.monitor.resources.index;

import cn.com.netease.nadp.monitor.annotations.NadpResource;
import cn.com.netease.nadp.monitor.model.ResultModel;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/3.
 * Description
 */
@NadpResource
@Path("/index")
public class IndexResource {
    @GET
    @Path("*")
    public Response  getConfig(Map<String,String> map,@Context HttpServletResponse response) {
        return Response.seeOther(UriBuilder.fromResource(IndexResource.class).path("/index.html").build()).build();
    }

}
