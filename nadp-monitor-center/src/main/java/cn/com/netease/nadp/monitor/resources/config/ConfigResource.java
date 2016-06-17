package cn.com.netease.nadp.monitor.resources.config;

import cn.com.netease.nadp.monitor.annotations.NadpResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * cn.com.netease.nadp.web.monitor.resources.monitor
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
@NadpResource
@Path("monitor")
public class ConfigResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/list")
    public String getConfig() {
        return "";
    }
}
