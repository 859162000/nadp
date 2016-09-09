package cn.com.netease.nadp.monitor.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * Created by bjbianlanzhou on 2016/9/2.
 */
public class AuthFilter implements ContainerRequestFilter {
    @Context
    private HttpServletRequest servletRequest;
    @Context
    private HttpServletResponse servletResponse;
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("1test filter:"+servletRequest.getRequestURL().toString());
        if(servletRequest.getSession().getAttribute("auth")==null){

        }
    }

}
