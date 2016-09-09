package cn.com.netease.nadp.monitor.common;

import cn.com.netease.nadp.monitor.service.auth.AuthService;
import cn.com.netease.nadp.monitor.utils.SpringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bjbianlanzhou on 2016/9/8.
 */
public class StaticFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(((HttpServletRequest)servletRequest).getRequestURL().toString().indexOf("login.html")<0){
            if(((HttpServletRequest) servletRequest).getSession().getAttribute("auth")==null){
                ((HttpServletResponse)servletResponse).sendRedirect("/login.html");
                return;
            }
        }else{
            ((HttpServletRequest) servletRequest).getSession().setAttribute("auth",null);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
